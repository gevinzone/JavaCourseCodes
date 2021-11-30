# 说明

可以通过GitHub上redis项目中的[create-cluster](https://github.com/redis/redis/blob/unstable/utils/create-cluster/create-cluster)脚本便捷的创建redis 集群

使用：

1. 确认脚本中的BIN_PATH是否与本级的`redis-server` 命令所在的目录一致，不一致可以修改
2. 修改集群节点数和起始端口号
3. 执行`bash ./create-cluster start`命令，启动redis实例
4. 执行`bash ./create-cluster create`命令完成集群实例的实际创建
5. 执行`bash ./create-cluster stop` 停止所有实例（后面可以再使用`bash ./create-cluster start`重启所有实例）
6. 集群停机后，执行`bash ./create-cluster clean`，会清理全部AOF / LOG 文件，以便新起一个集群环境


注：

1. 由于使用该脚本，只能单机起多个实例作redis集群，不适合生产环境使用。
2. 执行`bash ./create-cluster create`后，脚本同级目录下，会生成全部实例的aof、log、node.conf和数据文件，代码提交前已执行`bash ./create-cluster clean`全部清理


