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
package ac.shard.api.alert;

/**
 * Alert channels staff can subscribe to.
 *
 * <p>Each type is delivered only to subscribers with its permission. Constants may be added in
 * later versions, so a switch over this type needs a default branch.
 *
 * @see AlertService#setSubscribed(org.bukkit.entity.Player, AlertType, boolean)
 * @since 2.1.0
 */
public enum AlertType {
  /** Detection flags. Permission {@code shard.alerts}. */
  FLAG,
  /**
   * A player's buffer crossed the suspicious-alert level. Permission {@code
   * shard.suspicious.alerts}.
   */
  SUSPICIOUS,
  /** A player's mitigation rose to a higher tier. Permission {@code shard.mitigations.alerts}. */
  MITIGATION,
  /** Client brand notifications. Permission {@code shard.brand}. */
  BRAND,
  /**
   * Alerts sent by plugins through {@link AlertService}. Permission {@code shard.alerts.custom}.
   */
  CUSTOM
}
