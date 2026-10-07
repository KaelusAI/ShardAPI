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

import org.jetbrains.annotations.ApiStatus;

/**
 * Signals that a storage operation behind an API future failed.
 *
 * <p>Futures returned by database-backed service methods complete exceptionally with this exception
 * when the operation throws. {@link #getCause()} returns the original error.
 *
 * @since 2.1.0
 */
public final class ShardStorageException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception. Only Shard creates instances.
   *
   * @param message the detail message
   * @param cause the original error
   */
  @ApiStatus.Internal
  public ShardStorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
