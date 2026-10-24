# ktgen
Simple Gradle plugin hooking up kotlinpoet

The default task inputs and runtime classpath wrap their dependency configurations in lazy file
collections. This preserves producer task dependencies without retaining a `Configuration` directly
inside a task property, so the task can be stored in Gradle's configuration cache.
