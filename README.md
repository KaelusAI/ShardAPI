# ShardAPI

The public Java API of [Shard](https://shard.ac), the machine-learning anticheat for Paper, Folia and Spigot servers.
## Compatibility

| ShardAPI | Shard | Java |
|---|---|---|
| 2.1.x | 2.1.x | 17+ |

Shard 2.0 and older included a different API inside the plugin under the same `ac.shard.api` package. It is not compatible with ShardAPI.

## Installation

ShardAPI is published to Maven Central. Shard provides the API classes at runtime, so the dependency is `compileOnly` and the API is never shaded.

Gradle (Kotlin DSL):

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    compileOnly("ac.shard:shard-api:2.1.0")
}
```

Gradle (Groovy DSL):

```groovy
dependencies {
    compileOnly 'ac.shard:shard-api:2.1.0'
}
```

Maven:

```xml
<dependency>
    <groupId>ac.shard</groupId>
    <artifactId>shard-api</artifactId>
    <version>2.1.0</version>
    <scope>provided</scope>
</dependency>
```

Snapshots of the next version are published to `https://central.sonatype.com/repository/maven-snapshots/`.

The dependency is declared in `plugin.yml`: `depend` when the plugin needs Shard, `softdepend` when Shard is optional.

```yaml
softdepend: [Shard]
```

## Getting started

```java
public final class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        ShardProvider.find().ifPresent(shard -> {
            getLogger().info("Hooked into Shard " + shard.pluginVersion());
        });
    }
}
```

`ShardProvider.get()` throws an `IllegalStateException` that names the cause when Shard is missing, not enabled yet, or when the API was shaded into the plugin jar by mistake.

## Examples

Forwarding flags to an external system:

```java
shard.events().subscription(this, FlagEvent.class)
    .monitor()
    .ignoreCancelled(true)
    .subscribe(event -> discord.send(event.playerName() + " flagged by " + event.modelId()));
```

Skipping punishments for players inside a minigame:

```java
shard.exemptions().builder(this, player.getUniqueId())
    .scope(ExemptionScope.ENFORCEMENT)
    .duration(Duration.ofMinutes(30))
    .reason("arena")
    .grant();

shard.exemptions().revokeAll(this, player.getUniqueId());
```

Cancelling a punishment:

```java
shard.events().subscribe(this, PunishmentEvent.class, event -> {
    if (tournament.isRunning()) {
        event.setCancelled(true);
    }
});
```

Reading the detection state of an online player:

```java
shard.players().player(player).ifPresent(session -> {
    DetectionSnapshot detection = session.detection();
    for (ModelState model : detection.models()) {
        for (LabelBuffer buffer : model.buffers()) {
            sender.sendMessage(buffer.label() + ": " + buffer.value() + " / " + buffer.flagThreshold());
        }
    }
});
```

Looking up the history of any player, online or not:

```java
shard.players().lookup("Player")
    .thenCompose(found -> found
        .map(known -> shard.history().violations(known.playerId(), 0, 10))
        .orElseGet(() -> CompletableFuture.completedFuture(null)))
    .thenAccept(page -> {
        if (page != null) {
            page.items().forEach(record -> getLogger().info(record.createdAt() + " VL " + record.violationLevel()));
        }
    });
```

## Threading

- Getters and snapshots are safe to call from any thread and never block.
- Futures complete off the server thread. Code that touches the world goes through the Bukkit scheduler first, or the entity and region schedulers on Folia.
- Events that can be cancelled fire synchronously on the thread that makes the decision, which each event documents. Their handlers are expected to be short.
- All other events are delivered in order on a single Shard thread that is never a tick, region or network thread. Delivery is best effort, and under heavy load some events can be dropped.
- Subscriptions and exemptions are removed automatically when the owning plugin disables.

## Stability

- The API follows semantic versioning within a major version. Minor releases only add types, methods, enum constants and events.
- Interfaces are annotated `@ApiStatus.NonExtendable`. They are meant to be used, not implemented.
- Types annotated `@ApiStatus.Experimental` describe the machine-learning models and may change in a minor release.
- Every package is `@NullMarked` with [JSpecify](https://jspecify.dev). Anything that can be absent is marked `@Nullable` or returned as `Optional`.

## License

ShardAPI is licensed under the [Apache License 2.0](LICENSE). Shard itself is licensed separately.
