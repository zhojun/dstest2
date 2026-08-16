# IDEA + Git 实战教程

> 以本项目（myds，Spring Boot）为例，讲解 IDEA 里 Git 的实战用法。
> 目标：学完能独立完成"拉代码 → 写功能 → 提交 → 推送 → 合并"的日常开发循环。

## 0. 先看懂这张图：Git 的四个区域

```
 工作区         暂存区          本地仓库          远程仓库
(写代码的     (待提交的      (commit 历史)     (GitHub/Gitee
 文件)         文件快照)                        上的仓库)
   │  git add     │   commit      │    push        │
   │ ───────────> │ ────────────> │ ─────────────> │
   │              │               │                │
   │<─────────────────────────────└──── pull ──────┤
   └────────── 工作区的文件被 pull/checkout 更新 ───┘
```

- **工作区**：你在 IDEA 里改的文件
- **暂存区**：你勾选"这次要提交哪些改动"
- **本地仓库**：commit 之后，历史存在你电脑上
- **远程仓库**：push 之后，别人才能看到

理解这四步，Git 就懂了一半。IDEA 把每一步都变成了按钮和颜色。

## 1. 一次性配置（只做一次）

### 1.1 设置你的身份（提交记录上会显示）

```bash
git config --global user.name "你的名字"
git config --global user.email "你的邮箱"
```

### 1.2 IDEA 里关联 Git

`File → Settings → Version Control → Git`，确认 "Path to Git executable" 已自动检测到（通常无需改动）。

### 1.3 登录远程平台（可选，推送时再做也行）

- GitHub：`Settings → Version Control → GitHub`，点 + 登录
- Gitee（国内常用）：先装 Gitee 插件（`Settings → Plugins` 搜 Gitee），再在 `Version Control → Gitee` 登录

## 2. 项目首次提交（以 myds 为例）

1. **初始化仓库**：菜单 `VCS → Enable Version Control Integration → 选 Git → OK`
2. **检查 .gitignore**：本项目已生成好，忽略 `target/`（编译产物）、`.idea/`（IDE 个人配置）等。原则：**能重新生成的东西、和机器有关的东西，都不提交**
3. **首次提交**：
   - 项目树里文件变成**红色** = 未跟踪（新文件）
   - 按 `Ctrl+K` 打开 Commit 面板 → 勾选所有文件 → 写提交信息 `init: spring boot 项目初始化` → 点 Commit
4. **推送**：`Ctrl+Shift+K` → 首次会提示"没有远程仓库" → 点 "Define remote" 填入你创建的 GitHub/Gitee 仓库地址 → Push

> 提交信息规范（Conventional Commits，团队通用）：
> `feat: 新增登录接口` / `fix: 修复端口冲突` / `docs: 补充教程` / `refactor: 重命名变量`

## 3. 日常开发循环（核心！每天重复几十次）

这是实战中最重要的节奏，记住这 6 步：

```
① 开始前：Ctrl+T 拉取最新（多人协作必备，避免和别人改重）
② 写代码：正常开发
③ 随时看改了什么：
   - 文件颜色：红色=新文件  蓝色=已修改  绿色=已暂存  白色=没动过
   - 点文件 → 右键 Git → Compare with... 或直接看编辑器左侧的改动标记条
   - 双击文件在 Commit 面板里看 Diff：左旧右新
④ 提交：Ctrl+K → 勾选要提交的文件 → 写信息 → Commit
⑤ 推送：Ctrl+Shift+K → Push
⑥ 验证：远程仓库网页上能看到你的提交，完成
```

**三条铁律（老手都这么干）：**

1. **小步提交**：一个功能一个 commit，改到一半没想好也不要紧，先提交再说（本地提交随时可改）
2. **提交前必看 Diff**：勾选文件前，把每个改动过一遍，防止把调试代码、密码、无关注释提交上去
3. **绝不提交**：`target/`、`.idea/`、密码、密钥、大文件

## 4. 分支实战（团队协作的核心技能）

**为什么用分支**：`main` 分支永远保持可运行；开发新功能开分支，做完合并回去。

```
main ──●──●────────────●──●──  (合并 feature 后的历史)
             \
   feature/login ──●──●──●
```

**操作流程（全在 IDEA 完成）：**

1. 右下角点 `Git: main` → **New Branch** → 命名 `feature/hello-page`
2. 在这个分支上开发、提交、推送（推送时 IDEA 会提示 "new branch"）
3. 功能完成 → 去远程仓库网页发 **Pull Request**（GitHub）/ **Merge Request**（Gitee、GitLab）→ 合并
4. 合并完成后回 IDEA：右下角切回 `main` → `Ctrl+T` 拉最新 → 删除本地分支（右下角 → 分支名 → Delete）

**命名规范**（团队通用）：
```
feature/xxx   新功能
fix/xxx       修 bug
hotfix/xxx    紧急线上修复
refactor/xxx  重构
```

## 5. 冲突解决（新手最怕，其实有套路）

**什么时候发生**：两个人改了同一个文件的同一行。

**IDEA 三栏界面**（自动弹出，不用慌）：

```
│  你的改动(Accept Left)  │  合并结果(中间)  │  别人的改动(Accept Right)  │
```

**步骤：**

1. 中间栏是最终结果，逐处点击 `»` 或 `«` 决定要哪边（或者都保留）
2. 全部处理完 → 点 Apply
3. 重新 `Ctrl+K` 提交（冲突解决本身也是一次提交）
4. Push

**避免冲突的技巧**：开工前先 `Ctrl+T`；一个分支别活太久；少动别人正在改的文件。

## 6. "后悔药"（高频救命操作）

| 场景 | 操作 |
|---|---|
| 改动还没提交，想全部丢弃 | 左侧 Commit 面板 → 右键 Changes → Rollback |
| 单个文件想还原 | 文件右键 → Git → Rollback |
| 提交了但没推送，想改提交信息 | `Ctrl+K` 面板 → 右上角 Amend |
| 提交了但没推送，想撤销整个提交 | Log 面板 → 右键该提交 → Undo Commit（改动回到未提交状态，不丢） |
| **已经推送**，想撤销 | Log → 右键 → **Revert Commit**（生成一条"反向提交"，不抹掉历史，团队安全） |
| 改到一半要切分支，又不想提交 | 右键 → Git → **Stash Changes**（暂存起来）；回来用 Unstash 恢复 |
| 想找回某个历史版本的文件 | Log 面板选提交 → 右键文件 → Show Diff / Get |

> 新手唯一别碰的：`git push --force`（强推，会抹掉别人提交）和 `git reset --hard`（本地改动直接没了）。

## 7. 查看历史和追踪问题

- **Log 面板**：左侧边栏 Git 图标（或 `Alt+9`）→ Log，看到全部提交历史、分支图
- **每一行代码是谁写的**：行号处右键 → **Annotate with Git Blame** → 左侧列出每行的提交者和时间 → 点击能跳转到那次提交
- **对比任意两个版本**：Log 里按住 Ctrl 选两个提交 → 右键 Compare Versions
- **搜索**：双击 Shift → 切到 Git 标签，按提交信息、作者搜索

## 8. 快捷键速查表

| 操作 | 快捷键 |
|---|---|
| 拉取最新（Update Project） | `Ctrl+T` |
| 提交（Commit） | `Ctrl+K` |
| 推送（Push） | `Ctrl+Shift+K` |
| Git 面板（Log/分支） | `Alt+9` 或左下角图标 |
| 查看文件改动（Diff） | `Ctrl+D`（选中文件时） |
| 搜索一切 | 双击 `Shift` |
| 撤销上一次编辑 | `Ctrl+Z`（编辑器和 Git 无关但常用） |

## 9. 新手成长路线

1. **第一周**：只练第 3 节的循环——改、看 diff、提交、推送。单人开发够用了
2. **第二周**：练分支——每个新功能开分支，合并回 main
3. **第三周**：故意制造一次冲突（两台设备/两个分支改同一行），练第 5 节
4. **之后**：遇到"我改坏了想回去"，翻第 6 节的表

Git 不是要背命令的学科，是肌肉记忆。练会这张表上的场景，日常开发就够了。
