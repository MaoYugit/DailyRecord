数据库（database）--> 集合（collection）--> 文档（document）



database: 一般情况下一个项目只用一个数据库

collection: 一个users文档						collection: 一个accounts文档

document: users文档里面的每一个user			document: accounts文档里面的每一个account





显示所有数据库：`show dbs`

切换到指定数据库：`use 库名`

显示当前所在数据库：`db`

删除当前所在数据库：`use 库名；db.dropDatabase()`



创建集合：`db.createCollection('集合名称')`

显示当前数据库中所有集合：`show collections`

删除某个集合：`db.集合名.drop()`

重命名集合：`db.集合名.renameCollection('new name')`



插入文档：db.集合名.insert(文档对象)

查询文档：db.集合名.find('查询条件')  	_id 是 mongodb 自动生成的唯一编号，用来识别文档

更新文档： db.集合名.update(查询条件，新的文档) 	db.集合名.update({name: '张三'}， {$set: {age: 19}})  没加$set最终数据会只剩更新后的 age

删除文档：db.集合名.remove(查询条件) 



