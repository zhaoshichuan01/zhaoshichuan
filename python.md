### Python项目虚拟环境配置总结

#### 1. 推荐方式：Poetry
- 自动创建和管理虚拟环境
- 依赖管理简单，版本锁定
- 支持开发/生产依赖分离
- 适合团队协作和项目分享

**基本流程：**
```bash
mkdir 项目目录 && cd 项目目录
poetry init
poetry add 包名
poetry install
poetry shell
poetry run python 脚本.py
```

#### 2. Python自带 venv
- 轻量级，兼容性好
- 需手动管理依赖

**基本流程：**
```bash
cd /你的项目路径
python3 -m venv venv
激活虚拟环境:
source venv/bin/activate  # macOS/Linux
# venv\Scripts\activate   # Windows
pip install 包名
deactivate 退出虚拟环境
```

#### 3. Conda（数据科学常用）
- 适合需要科学计算库的项目
- 环境导出方便

**基本流程：**
```bash
conda create -n 环境名 python=版本
conda activate 环境名
conda install 包名
conda env export > environment.yml
conda deactivate
```

#### 4. 最佳实践
- 每个项目都用虚拟环境，避免全局污染
- 提交 lock 文件，确保依赖一致
- .gitignore 排除虚拟环境目录和敏感文件
- README 记录 Python 版本和依赖说明

**.gitignore 示例：**
```bash
venv/
.venv/
env/
__pycache__/
*.pyc
.env
```

#### 5. 你的项目建议
- 已用 Poetry，继续用即可
- 新项目可直接复制 pyproject.toml 或重新 init
- 依赖管理和环境激活都用 Poetry 命令完成

如需模板或自动化脚本可随时告知！