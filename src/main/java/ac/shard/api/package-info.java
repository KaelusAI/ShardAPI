/**
 * Public API of the Shard anticheat.
 *
 * <p>The entry point is {@link ac.shard.api.ShardProvider#get()}, which returns the {@link
 * ac.shard.api.Shard} root. The root hands out one service per area (players, detection,
 * punishments, mitigation, exemptions, alerts, history, network) and the {@link
 * ac.shard.api.event.EventBus}. Types in this package are shared by those services. Every type is
 * null-marked, so a value is non-null unless annotated {@code @Nullable}.
 *
 * @since 2.1.0
 */
@NullMarked
package ac.shard.api;

import org.jspecify.annotations.NullMarked;
