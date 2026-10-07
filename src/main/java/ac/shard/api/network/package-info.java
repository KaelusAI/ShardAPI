/**
 * State shared between Shard servers over Redis, such as suspicious players published by other
 * servers.
 *
 * <p>The entry point is {@link ac.shard.api.network.NetworkService}, obtained from {@link
 * ac.shard.api.Shard#network()}.
 *
 * @since 2.1.0
 */
@NullMarked
package ac.shard.api.network;

import org.jspecify.annotations.NullMarked;
