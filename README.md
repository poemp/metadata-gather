
**数据采集小工具**


**简介**：数据采集 接口

**HOST**:127.0.0.1:10015

**联系人**:poem

**Version**:1.0.0

**接口路径**：/v2/api-docs?group=API-All


# 01-元数据采集

## 0103-删除所有保存的连接信息

**接口说明**:删除所有保存的连接信息


**接口地址**:`/v1/gather/deleteAllDataGatherInfoId`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 说明     |     in |  是否必须      |  类型   |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|gatherId| 连接信息id  | query | true |string  |    |

**响应数据**:

```json

```

**响应参数说明**:


暂无





**响应状态码说明**:


| 状态码         | 说明                             |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
| 201 | Created  ||
| 400 | 请求参数没有填好  ||
| 401 | Unauthorized  ||
| 403 | Forbidden  ||
| 404 | 请求路径没有找到  ||
| 500 | 数据库发生错误  ||
## 0101-获取所有的连接信息

**接口说明**:获取所有的连接信息


**接口地址**:`/v1/gather/getAllGather`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：
暂无



**响应数据**:

```json
[
	{
		"dbId": "",
		"gatherDBVOS": [
			{
				"dbVO": {
					"gatherId": "",
					"id": "",
					"name": ""
				},
				"gatherTableVOS": [
					{
						"tableFieldsVOS": [
							{
								"dataType": "",
								"defaultValue": "",
								"description": "",
								"field": "",
								"tableId": ""
							}
						],
						"tableVO": {
							"dbId": "",
							"id": "",
							"name": "",
							"table": ""
						}
					}
				]
			}
		],
		"gatherId": "",
		"gratherid": "",
		"id": "",
		"ip": "",
		"name": "",
		"password": "",
		"port": "",
		"serverName": "",
		"type": "",
		"userName": ""
	}
]
```

**响应参数说明**:


| 参数名称         | 说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|dbId| 为空  |string  |    |
|gatherDBVOS| 数据库信息  |array  | 数据库表信息   |
|gatherId| 为空  |string  |    |
|gratherid| 连接信息Id  |string  |    |
|id| id 新添加任务为空  |string  |    |
|ip| 地址  |string  |    |
|name| 连接名字  |string  |    |
|password| 密码  |string  |    |
|port| 端口  |string  |    |
|serverName| oracle 服务名字  |string  |    |
|type| 数据类型 MYSQL ORACLE  |string  |    |
|userName| 用户名  |string  |    |



**schema属性说明**




**数据库表信息**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|dbVO | 数据库   |数据库  | 数据库   |
|gatherTableVOS | 表   |array  | 数据表和列   |

**数据库**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|gatherId | 连接信息id   |string  |    |
|id | id   |string  |    |
|name | 数据库名字   |string  |    |

**数据表和列**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|tableFieldsVOS | 数据表列   |array  | 数据表列   |
|tableVO | 数据表   |数据表  | 数据表   |

**数据表列**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|dataType | 列的数据类型   |string  |    |
|defaultValue | 默认值   |string  |    |
|description | 表的描述   |string  |    |
|field | 列id   |string  |    |
|tableId | 表id   |string  |    |

**数据表**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|dbId | 库id   |string  |    |
|id | id   |string  |    |
|name | 表的中文注释名字   |string  |    |
|table | 表名字   |string  |    |

**响应状态码说明**:


| 状态码         | 说明                             |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |连接信息|
| 400 | 请求参数没有填好  ||
| 401 | Unauthorized  ||
| 403 | Forbidden  ||
| 404 | 请求路径没有找到  ||
| 500 | 数据库发生错误  ||
## 0103-根据信息连接信息获取库、表、字端

**接口说明**:根据信息连接信息获取库、表、字端


**接口地址**:`/v1/gather/getDBAndTableAndFields`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 说明     |     in |  是否必须      |  类型   |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|db| 数据库  | query | false |string  |    |
|dbId| 数据库id  | query | false |string  |    |
|fields| 字端信息  | query | false |string  |    |
|fieldsDes| 字端表述  | query | false |string  |    |
|gratherid| 连接信息Id  | query | false |string  |    |
|table| 表  | query | false |string  |    |
|tableId| 表id  | query | false |string  |    |

**响应数据**:

```json
{
	"dbId": "",
	"gatherDBVOS": [
		{
			"dbVO": {
				"gatherId": "",
				"id": "",
				"name": ""
			},
			"gatherTableVOS": [
				{
					"tableFieldsVOS": [
						{
							"dataType": "",
							"defaultValue": "",
							"description": "",
							"field": "",
							"tableId": ""
						}
					],
					"tableVO": {
						"dbId": "",
						"id": "",
						"name": "",
						"table": ""
					}
				}
			]
		}
	],
	"gatherId": "",
	"gratherid": "",
	"id": "",
	"ip": "",
	"name": "",
	"password": "",
	"port": "",
	"serverName": "",
	"type": "",
	"userName": ""
}
```

**响应参数说明**:


| 参数名称         | 说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|dbId| 为空  |string  |    |
|gatherDBVOS| 数据库信息  |array  | 数据库表信息   |
|gatherId| 为空  |string  |    |
|gratherid| 连接信息Id  |string  |    |
|id| id 新添加任务为空  |string  |    |
|ip| 地址  |string  |    |
|name| 连接名字  |string  |    |
|password| 密码  |string  |    |
|port| 端口  |string  |    |
|serverName| oracle 服务名字  |string  |    |
|type| 数据类型 MYSQL ORACLE  |string  |    |
|userName| 用户名  |string  |    |



**schema属性说明**




**数据库表信息**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|dbVO | 数据库   |数据库  | 数据库   |
|gatherTableVOS | 表   |array  | 数据表和列   |

**数据库**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|gatherId | 连接信息id   |string  |    |
|id | id   |string  |    |
|name | 数据库名字   |string  |    |

**数据表和列**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|tableFieldsVOS | 数据表列   |array  | 数据表列   |
|tableVO | 数据表   |数据表  | 数据表   |

**数据表列**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|dataType | 列的数据类型   |string  |    |
|defaultValue | 默认值   |string  |    |
|description | 表的描述   |string  |    |
|field | 列id   |string  |    |
|tableId | 表id   |string  |    |

**数据表**

| 参数名称         |  说明          |   类型  |  schema |
| ------------ | ------------------|--------|----------- |
|dbId | 库id   |string  |    |
|id | id   |string  |    |
|name | 表的中文注释名字   |string  |    |
|table | 表名字   |string  |    |

**响应状态码说明**:


| 状态码         | 说明                             |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |连接信息|
| 400 | 请求参数没有填好  ||
| 401 | Unauthorized  ||
| 403 | Forbidden  ||
| 404 | 请求路径没有找到  ||
| 500 | 数据库发生错误  ||
## 0109-数据库-根据信息连接信息获取库、表、字端,并且保存到数据库中

**接口说明**:数据库-根据信息连接信息获取库、表、字端,并且保存到数据库中


**接口地址**:`/v1/gather/getDbAndTableAndFieldAndSave`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 说明     |     in |  是否必须      |  类型   |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|gatherId| 连接信息id  | query | true |string  |    |

**响应数据**:

```json

```

**响应参数说明**:


暂无





**响应状态码说明**:


| 状态码         | 说明                             |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
| 201 | Created  ||
| 400 | 请求参数没有填好  ||
| 401 | Unauthorized  ||
| 403 | Forbidden  ||
| 404 | 请求路径没有找到  ||
| 500 | 数据库发生错误  ||
## 0106-数据库-获取数据的schema

**接口说明**:数据库-获取数据的schema


**接口地址**:`/v1/gather/getSchema/{gatherId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 说明     |     in |  是否必须      |  类型   |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|gatherId| 连接信息id  | path | true |string  |    |

**响应数据**:

```json
[
	{
		"gatherId": "",
		"id": "",
		"name": ""
	}
]
```

**响应参数说明**:


| 参数名称         | 说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|gatherId| 连接信息id  |string  |    |
|id| id  |string  |    |
|name| 数据库名字  |string  |    |





**响应状态码说明**:


| 状态码         | 说明                             |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |数据库|
| 400 | 请求参数没有填好  ||
| 401 | Unauthorized  ||
| 403 | Forbidden  ||
| 404 | 请求路径没有找到  ||
| 500 | 数据库发生错误  ||
## 0107-数据库-获取数据表字端信息

**接口说明**:获取数据表字端信息


**接口地址**:`/v1/gather/getTable`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 说明     |     in |  是否必须      |  类型   |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dbId| 为空  | query | false |string  |    |
|gatherId| 为空  | query | false |string  |    |
|id| id 新添加任务为空  | query | false |string  |    |
|ip| 地址  | query | false |string  |    |
|name| 连接名字  | query | false |string  |    |
|password| 密码  | query | false |string  |    |
|port| 端口  | query | false |string  |    |
|serverName| oracle 服务名字  | query | false |string  |    |
|type| 数据类型 MYSQL ORACLE  | query | false |string  |    |
|userName| 用户名  | query | false |string  |    |

**响应数据**:

```json
[
	{
		"dbId": "",
		"id": "",
		"name": "",
		"table": ""
	}
]
```

**响应参数说明**:


| 参数名称         | 说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|dbId| 库id  |string  |    |
|id| id  |string  |    |
|name| 表的中文注释名字  |string  |    |
|table| 表名字  |string  |    |





**响应状态码说明**:


| 状态码         | 说明                             |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |数据表|
| 400 | 请求参数没有填好  ||
| 401 | Unauthorized  ||
| 403 | Forbidden  ||
| 404 | 请求路径没有找到  ||
| 500 | 数据库发生错误  ||
## 0108-数据库-获取表的字端信息

**接口说明**:获取表的字端信息


**接口地址**:`/v1/gather/getTableFieles`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 说明     |     in |  是否必须      |  类型   |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dbId| 库id  | query | false |string  |    |
|id| id  | query | false |string  |    |
|name| 表的中文注释名字  | query | false |string  |    |
|table| 表名字  | query | false |string  |    |

**响应数据**:

```json
[
	{
		"dataType": "",
		"defaultValue": "",
		"description": "",
		"field": "",
		"tableId": ""
	}
]
```

**响应参数说明**:


| 参数名称         | 说明                             |    类型 |  schema |
| ------------ | -------------------|-------|----------- |
|dataType| 列的数据类型  |string  |    |
|defaultValue| 默认值  |string  |    |
|description| 表的描述  |string  |    |
|field| 列id  |string  |    |
|tableId| 表id  |string  |    |





**响应状态码说明**:


| 状态码         | 说明                             |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  |数据表列|
| 400 | 请求参数没有填好  ||
| 401 | Unauthorized  ||
| 403 | Forbidden  ||
| 404 | 请求路径没有找到  ||
| 500 | 数据库发生错误  ||
## 0102-保存连接信息

**接口说明**:保存连接信息


**接口地址**:`/v1/gather/saveGatherInfo`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`


**请求示例**：
```json
{
	"dbId": "",
	"gatherDBVOS": [
		{
			"dbVO": {
				"gatherId": "",
				"id": "",
				"name": ""
			},
			"gatherTableVOS": [
				{
					"tableFieldsVOS": [
						{
							"dataType": "",
							"defaultValue": "",
							"description": "",
							"field": "",
							"tableId": ""
						}
					],
					"tableVO": {
						"dbId": "",
						"id": "",
						"name": "",
						"table": ""
					}
				}
			]
		}
	],
	"gatherId": "",
	"gratherid": "",
	"id": "",
	"ip": "",
	"name": "",
	"password": "",
	"port": "",
	"serverName": "",
	"type": "",
	"userName": ""
}
```


**请求参数**：

| 参数名称         | 说明     |     in |  是否必须      |  类型   |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|gatherDBTableFieldsVO| gatherDBTableFieldsVO  | body | true |连接信息  | 连接信息   |

**schema属性说明**



**连接信息**

| 参数名称         | 说明    |     in |  是否必须   |  类型  |  schema |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dbId| 为空  | body | false |string  |    |
|gatherDBVOS| 数据库信息  | body | false |array  | 数据库表信息   |
|gatherId| 为空  | body | false |string  |    |
|gratherid| 连接信息Id  | body | false |string  |    |
|id| id 新添加任务为空  | body | false |string  |    |
|ip| 地址  | body | false |string  |    |
|name| 连接名字  | body | false |string  |    |
|password| 密码  | body | false |string  |    |
|port| 端口  | body | false |string  |    |
|serverName| oracle 服务名字  | body | false |string  |    |
|type| 数据类型 MYSQL ORACLE  | body | false |string  |    |
|userName| 用户名  | body | false |string  |    |

**数据库表信息**

| 参数名称         | 说明    |     in |  是否必须   |  类型  |  schema |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dbVO| 数据库  | body | false |数据库  | 数据库   |
|gatherTableVOS| 表  | body | false |array  | 数据表和列   |

**数据库**

| 参数名称         | 说明    |     in |  是否必须   |  类型  |  schema |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|gatherId| 连接信息id  | body | false |string  |    |
|id| id  | body | false |string  |    |
|name| 数据库名字  | body | false |string  |    |

**数据表和列**

| 参数名称         | 说明    |     in |  是否必须   |  类型  |  schema |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|tableFieldsVOS| 数据表列  | body | false |array  | 数据表列   |
|tableVO| 数据表  | body | false |数据表  | 数据表   |

**数据表列**

| 参数名称         | 说明    |     in |  是否必须   |  类型  |  schema |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dataType| 列的数据类型  | body | false |string  |    |
|defaultValue| 默认值  | body | false |string  |    |
|description| 表的描述  | body | false |string  |    |
|field| 列id  | body | false |string  |    |
|tableId| 表id  | body | false |string  |    |

**数据表**

| 参数名称         | 说明    |     in |  是否必须   |  类型  |  schema |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|dbId| 库id  | body | false |string  |    |
|id| id  | body | false |string  |    |
|name| 表的中文注释名字  | body | false |string  |    |
|table| 表名字  | body | false |string  |    |

**响应数据**:

```json

```

**响应参数说明**:


暂无





**响应状态码说明**:


| 状态码         | 说明                             |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
| 201 | Created  ||
| 400 | 请求参数没有填好  ||
| 401 | Unauthorized  ||
| 403 | Forbidden  ||
| 404 | 请求路径没有找到  ||
| 500 | 数据库发生错误  ||
## 0105-数据库-测试连接是否正常

**接口说明**:数据库-测试连接是否正常


**接口地址**:`/v1/gather/testGather/{gatherId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称         | 说明     |     in |  是否必须      |  类型   |  schema  |
| ------------ | -------------------------------- |-----------|--------|----|--- |
|gatherId| 连接信息id  | path | true |string  |    |

**响应数据**:

```json

```

**响应参数说明**:


暂无





**响应状态码说明**:


| 状态码         | 说明                             |    schema                         |
| ------------ | -------------------------------- |---------------------- |
| 200 | OK  ||
| 400 | 请求参数没有填好  ||
| 401 | Unauthorized  ||
| 403 | Forbidden  ||
| 404 | 请求路径没有找到  ||
| 500 | 数据库发生错误  ||
