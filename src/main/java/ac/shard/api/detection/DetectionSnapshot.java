/*
 * This file is part of ShardAPI, licensed under the Apache License 2.0.
 *
 *  Copyright (c) 2026 KaelusAI
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package ac.shard.api.detection;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Immutable detection state of one player at the time the snapshot was taken.
 *
 * <p>All buffers are taken atomically. {@link #lastVerdict(String)} and {@link #status()} are read
 * right after them and may already reflect a verdict or state change that came later.
 *
 * @see ac.shard.api.player.ShardPlayer#detection()
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface DetectionSnapshot {
  /**
   * Returns whether the player is being analysed, and if not, why.
   *
   * @return the detection status
   */
  DetectionStatus status();

  /**
   * Returns the buffers of each active model, in the order of the model profile.
   *
   * @return one entry per active model, empty if no model profile was received yet
   */
  @Unmodifiable
  List<ModelState> models();

  /**
   * Returns the buffers of one active model.
   *
   * @param modelId the model id
   * @return the model's state, empty if no active model has this id
   */
  Optional<ModelState> model(String modelId);

  /**
   * Returns the buffer of one label.
   *
   * @param label the label
   * @return the buffer, empty if the player has no tracked buffer for this label
   */
  Optional<LabelBuffer> buffer(LabelId label);

  /**
   * Returns the last verdict a model produced for the player.
   *
   * @param modelId the model id
   * @return the verdict, empty if the model produced no verdict this session
   */
  Optional<Verdict> lastVerdict(String modelId);
}
