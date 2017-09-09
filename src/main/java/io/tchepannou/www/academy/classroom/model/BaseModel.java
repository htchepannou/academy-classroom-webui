package io.tchepannou.www.academy.classroom.model;

public abstract class BaseModel {
    public Integer id;

    public final Integer getId() {
        return id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object that){
        if (that == null || !that.getClass().equals(getClass())){
            return false;
        }

        Integer id = getId();
        if (id == null){
            return super.equals(that);
        } else {
            return id.equals(((BaseModel)that).getId());
        }
    }

    @Override
    public int hashCode(){
        Integer id = getId();
        return id == null ? super.hashCode() : id.hashCode();
    }
}
