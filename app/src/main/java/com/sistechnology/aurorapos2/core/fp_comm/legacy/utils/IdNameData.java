package com.sistechnology.aurorapos2.core.fp_comm.legacy.utils;

/**
 * Created by MARIELA on 24.8.2016 г..
 */
public class IdNameData {
    private String fullName;
    private String name;
    private long id;
    private String tableName;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }




    public String getFullName() {
        return fullName;
    }

    public void setFullName(String _fullName) {
        fullName = _fullName;
    }


    public long getId() {return id;}
    public void setId(long id) {this.id = id;}


    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public boolean isNew() { return getId() < 1; }

    public IdNameData() { name = ""; id = -1;}
    public IdNameData(long id, String name) { this.name = name; this.id = id;}

    public void CopyFrom(IdNameData a)
    {
        if(a != null)
        {
            setId(a.getId());
            setName(a.getName());
        }
    }

    public void Clear() {id = 0; name = ""; }

    @Override
    public String toString(){
        return this.getName();
    }


}
