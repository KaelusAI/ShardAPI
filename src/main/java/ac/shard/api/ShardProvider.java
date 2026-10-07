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
package ac.shard.api;

import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Looks up the running Shard in the Bukkit services manager.
 *
 * <p>Shard registers its {@link Shard} instance when it enables and unregisters it when it
 * disables.
 *
 * @see Shard
 * @since 2.1.0
 */
public final class ShardProvider {
  private ShardProvider() {
    throw new AssertionError();
  }

  /**
   * Returns the running Shard.
   *
   * <p>Call from {@code onEnable} or later, with Shard listed in {@code depend} or {@code
   * softdepend}.
   *
   * @return the registered Shard instance
   * @throws IllegalStateException if no Shard is registered. The message names the cause: ShardAPI
   *     shaded into the calling plugin, Shard not installed, or Shard not enabled yet.
   */
  public static Shard get() {
    return find().orElseThrow(() -> new IllegalStateException(diagnose()));
  }

  /**
   * Returns the running Shard if one is registered.
   *
   * @return the registered Shard instance, or an empty {@link Optional} if Shard is not installed,
   *     not enabled yet or already disabled
   */
  public static Optional<Shard> find() {
    RegisteredServiceProvider<Shard> registration =
        Bukkit.getServicesManager().getRegistration(Shard.class);
    return registration == null ? Optional.empty() : Optional.of(registration.getProvider());
  }

  private static String diagnose() {
    for (Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
      if (service.getName().equals(Shard.class.getName()) && service != Shard.class) {
        String jar = Shard.class.getProtectionDomain().getCodeSource().getLocation().toString();
        return "ShardAPI is shaded into " + jar + "; depend on it compileOnly";
      }
    }
    Plugin plugin = Bukkit.getPluginManager().getPlugin("Shard");
    if (plugin == null) {
      return "Shard is not installed";
    }
    return "Shard is not enabled yet: add depend/softdepend and call from onEnable, not from a"
        + " constructor or onLoad";
  }
}
