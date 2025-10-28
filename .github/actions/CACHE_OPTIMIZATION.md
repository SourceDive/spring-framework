# GitHub Actions 缓存优化建议

## 当前缓存状态

✅ **已实现的缓存**：
1. Gradle 依赖缓存（通过 `gradle/actions/setup-gradle`）
2. Gradle 构建缓存（`org.gradle.caching=true`）
3. Gradle Wrapper 缓存

## 建议的优化

### 1. 升级 gradle/actions/setup-gradle 版本
当前版本：v3.5.0
最新版本：v5.0.0

**更新方法**：
```yaml
- name: Set Up Gradle
  uses: gradle/actions/setup-gradle@v5.0.0
  with:
    cache-read-only: false
    develocity-access-key: ${{ inputs.develocity-access-key }}
    dependency-graph: generate
    gradle-home-cache-includes: |
      caches
      notifications
      jdks
      wrapper
      buildSrc/build
    gradle-home-cache-cleanup: true
```

### 2. 添加额外的缓存层

#### a. Maven 本地仓库缓存
```yaml
- name: 缓存 Maven 本地仓库
  uses: actions/cache@v4
  with:
    path: ~/.m2/repository
    key: ${{ runner.os }}-maven-${{ hashFiles('**/build.gradle', '**/gradle.properties') }}
    restore-keys: |
      ${{ runner.os }}-maven-
```

#### b. 测试结果缓存
```yaml
- name: 缓存测试结果
  uses: actions/cache@v4
  with:
    path: |
      **/build/test-results/
      **/build/reports/
    key: ${{ runner.os }}-test-results-${{ github.sha }}
    restore-keys: |
      ${{ runner.os }}-test-results-
```

#### c. 编译输出缓存
```yaml
- name: 缓存编译输出
  uses: actions/cache@v4
  with:
    path: |
      **/build/classes/
      **/build/generated/
      **/build/libs/
    key: ${{ runner.os }}-build-output-${{ github.sha }}
    restore-keys: |
      ${{ runner.os }}-build-output-
```

### 3. Gradle 配置优化

在 GitHub Actions 中添加以下 Gradle 属性：
```properties
org.gradle.caching=true
org.gradle.parallel=true
org.gradle.configureondemand=true
org.gradle.jvmargs=-Xmx4g -XX:+HeapDumpOnOutOfMemoryError
```

### 4. 使用远程构建缓存（可选）

如果有 Gradle Enterprise 或其他远程缓存服务：
```properties
org.gradle.caching=true
org.gradle.cache.remote.enabled=true
org.gradle.cache.remote.url=https://your-cache-server/cache/
```

### 5. 缓存策略优化

#### 为不同的工作流设置不同的缓存策略：
- **PR 构建**：使用 `cache-read-only: true` 避免缓存污染
- **主分支构建**：使用 `cache-read-only: false` 更新缓存
- **Release 构建**：可能需要清理缓存以确保干净构建

### 6. 监控缓存效果

使用 GitHub Actions 的缓存分析功能：
1. 查看缓存命中率
2. 监控缓存大小
3. 定期清理过期缓存

## 实施优先级

1. **高优先级**：升级 gradle/actions/setup-gradle 到 v5.0.0
2. **中优先级**：添加测试结果和编译输出缓存
3. **低优先级**：配置远程构建缓存

## 预期效果

- 构建时间减少 30-50%
- 依赖下载时间大幅减少
- 测试执行时间通过缓存优化
- 减少 CI/CD 资源消耗