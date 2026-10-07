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

/**
 * Semantic version of the API.
 *
 * <p>The {@code COMPILED_*} constants are inlined into the consumer at compile time. Compare them
 * with {@link Shard#apiVersion()} to detect running on an older API, for example {@code
 * shard.apiVersion().isCompatibleWith(ApiVersion.COMPILED_MAJOR, ApiVersion.COMPILED_MINOR)}.
 *
 * @param major the major version, incremented on incompatible changes
 * @param minor the minor version, incremented when API is added
 * @param patch the patch version
 * @see Shard#apiVersion()
 * @since 2.1.0
 */
public record ApiVersion(int major, int minor, int patch) implements Comparable<ApiVersion> {
  /** Major version of the API the consumer was compiled against. */
  public static final int COMPILED_MAJOR = 2;

  /** Minor version of the API the consumer was compiled against. */
  public static final int COMPILED_MINOR = 1;

  /**
   * Creates a version from its parts.
   *
   * @param major the major version
   * @param minor the minor version
   * @param patch the patch version
   * @throws IllegalArgumentException if any part is negative
   */
  public ApiVersion {
    if (major < 0 || minor < 0 || patch < 0) {
      throw new IllegalArgumentException(
          "Negative version part: " + major + "." + minor + "." + patch);
    }
  }

  /**
   * Returns whether code compiled against {@code major.minor} can run on this version.
   *
   * @param major the required major version, which must match exactly
   * @param minor the lowest acceptable minor version
   * @return {@code true} if the major versions are equal and this minor version is at least {@code
   *     minor}
   */
  public boolean isCompatibleWith(int major, int minor) {
    return this.major == major && this.minor >= minor;
  }

  /**
   * Compares by major, then minor, then patch version.
   *
   * @param other the version to compare with
   * @return a negative number, zero or a positive number if this version is lower than, equal to or
   *     higher than {@code other}
   */
  @Override
  public int compareTo(ApiVersion other) {
    if (major != other.major) {
      return Integer.compare(major, other.major);
    }
    if (minor != other.minor) {
      return Integer.compare(minor, other.minor);
    }
    return Integer.compare(patch, other.patch);
  }

  /**
   * Returns the version as {@code major.minor.patch}, for example {@code 2.1.0}.
   *
   * @return the formatted version
   */
  @Override
  public String toString() {
    return major + "." + minor + "." + patch;
  }
}
