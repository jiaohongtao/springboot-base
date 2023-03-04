package com.hong.test.company;

import lombok.Data;
import org.apache.geode.DataSerializable;
import org.apache.geode.DataSerializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

@Data
public class UserBean implements DataSerializable {
    private int id;
    private int age;
    private String name;
    private Date createDate;

    public UserBean() {
    }

    public UserBean(int id, int age, String name, Date createDate) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.createDate = createDate;
    }

    @Override
    public void fromData(DataInput in) throws IOException, ClassNotFoundException {
        this.id = in.readInt();
        this.age = in.readInt();
        this.name = in.readUTF();
        this.createDate = DataSerializer.readDate(in);

    }

    @Override
    public void toData(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeInt(this.age);
        out.writeUTF(this.name);
        DataSerializer.writeDate(this.createDate, out);

    }

    @Override
    public String toString() {
        return "用户的信息为：id：" + id + " 年龄为：" + age + " 名字为： " + name + " 创建日期为：" + createDate + "!";

    }
    //此处省略set，get方法~
}   