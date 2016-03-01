#tg-tcp-framework
通讯协议1.0

报头+内容，报头固定30字节

[headTag][encode][encrypt][state][type][extend][sessionId][length][cmdId][......]
  byte     byte     byte    byte  byte   byte      long      int    int  {length}

 headTag: 固定值0x01
 encode: 0,UTF-8; 1,GBK; 2,GB2312; 3,ISO8859-1
 encrypt: 加密类型 0，不加密；其他待定
 state: 接口返回类型 0，正常执行操作失败；1，正常执行返回; 2，推送消息； 3，特殊显示消息； 4，服务端程序执行错误
 type: 数据内容格式  0，json; 1,protobuf
 extend: 备用字段
 sessionId: 会话id, 8字节long 类型
 length： 内容报文长度
 cmdId: 接口ID,长度4字节,前后端共享一份 cmdId 接口名对应文档