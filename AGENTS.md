# ktgen

先读 [README](README.md)和实际构建配置。本仓库是 Kotlin 代码生成的 Gradle 插件、processor API 与 runtime，不承载消费者的业务模型；根文件保持简短。

## 设计与编码

- 保持 plugin、processor API 与生成 runtime 的边界；按本仓库配置维护工具链，不从 Fornax 或 Caelum 机械复制 JDK 要求。
- 优先 `val` 和清楚的局部类型推导，公开 API 明确类型与合同；提前返回用于减小嵌套，保留有意义的上下文名称。简单逻辑留在调用处，辅助函数只为复用、复杂边界或清楚的独立概念提取。
- Gradle DSL、输入路径和 processor 输出在负责的边界解释并验证一次，内部使用明确模型，不反复重验已经成立的条件。保留 I/O／processor 失败与输出目录的所有权约束；陈旧产物清理只作用于本任务拥有的输出。
- 保留惰性文件集合、producer task 依赖和配置缓存兼容性；不把 `Configuration` 直接存入 task property，也不为方便在配置阶段提前解析依赖。
- 不为假想复用、竞态或防御增加框架与额外状态。异常在确有恢复、转换或清理责任的边界处理，不捕获后返回空生成结果假装成功；同步转换不无故改成异步。
- 生成问题修改 processor／输入和接线，不手改生成产物。冷构建代码按可读性选循环或集合操作；大输入路径避免重复扫描与无谓物化，性能说法须有代表性输入和测量。注释说明约束与原因。

## 测试与验收

- 任务语义改动使用最小真实 Gradle 消费工程和实际 processor，检查生成文件确实被消费者编译；不以 mock Gradle 对象或复制生成算法代替接线。
- 按影响选择 producer 任务依赖、输入变化、陈旧输出清理、失败传播及配置缓存首次运行／复用。预期文件／语义来自明确合同或独立 fixture；修复保留能区分旧错与新对的失败反例。
- 不要求每次机械改动新增测试；先复用适用证据，只验受影响范围。纯文档只查 diff、链接与规则一致性，不构建。
- 记录插件与消费者版本、实际执行任务、产物和未验范围；UP-TO-DATE／FROM-CACHE 与实际 processor 执行分开，编译插件成功不等于消费者生成成功。充分证据齐备即收口。
- 保留他人的工作树改动和提交历史；修改公共 API 时核对现有消费者，不顺带扩大工具链或发布范围。

代码参考禁止 DH、Voxy、Iris、Sodium、OptiFine 及其衍生代码；其他来源限制见 [Fornax 参考规则](https://github.com/Echonolix/fornax/blob/main/docs/references.md)。

编程原则参考 [OpenCode AGENTS.md](https://github.com/anomalyco/opencode/blob/a882cf98a0f9eb94b2231229159ae6e01d7e4a72/AGENTS.md)（2026-10-28）；按 Kotlin／Gradle 适配，消费者验证、独立预期和验收边界为项目补充。
