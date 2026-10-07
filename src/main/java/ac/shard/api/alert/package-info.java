/**
 * Staff alerts: subscriptions to alert channels and alerts sent by plugins.
 *
 * <p>The entry point is {@link ac.shard.api.alert.AlertService}, obtained from {@link
 * ac.shard.api.Shard#alerts()}. Alerts about to be delivered are observable through {@link
 * ac.shard.api.event.alert.AlertEvent}.
 *
 * @since 2.1.0
 */
@NullMarked
package ac.shard.api.alert;

import org.jspecify.annotations.NullMarked;
