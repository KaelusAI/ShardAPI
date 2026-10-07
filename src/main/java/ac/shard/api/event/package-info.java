/**
 * Events Shard publishes and the bus that delivers them.
 *
 * <p>The entry point is {@link ac.shard.api.event.EventBus}, obtained from {@link
 * ac.shard.api.Shard#events()}. Only Shard posts events. Handlers can run on any thread, network
 * threads included, so a handler must go through the scheduler before it touches the Bukkit API.
 * Subpackages hold the concrete events of each area.
 *
 * @since 2.1.0
 */
@NullMarked
package ac.shard.api.event;

import org.jspecify.annotations.NullMarked;
