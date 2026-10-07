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

import java.time.Instant;
import java.util.Map;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * One answer of a model about one window of play.
 *
 * @see DetectionSnapshot#lastVerdict(String)
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
@ApiStatus.Experimental
public interface Verdict {
  /**
   * Returns the id of the model that produced the verdict.
   *
   * @return the model id
   */
  String modelId();

  /**
   * Returns when Shard processed the verdict.
   *
   * @return the server wall-clock time at which the verdict was applied
   */
  Instant receivedAt();

  /**
   * Returns the highest cheat probability of the verdict.
   *
   * @return the highest cheat-label probability in [0,1], legit labels excluded. If the answer
   *     could not be attributed to labels, the highest raw score the model returned.
   */
  double probability();

  /**
   * Returns the probabilities by public label.
   *
   * <p>Legit labels are never present. For a multi-class model the map holds at most the winning
   * label.
   *
   * @return the probabilities in [0,1]. Empty when the answer was merged into the unattributed
   *     buffer, as for single-head models, or could not be attributed to labels.
   */
  @Unmodifiable
  Map<LabelId, Double> probabilities();
}
