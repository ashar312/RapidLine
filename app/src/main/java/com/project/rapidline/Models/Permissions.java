package com.project.rapidline.Models;

public class Permissions {

    private boolean addBail;
    private boolean deleteBail;
    private boolean updateBail;

    public Permissions(){

    }

    public Permissions(boolean addBail, boolean deleteBail, boolean updateBail) {
        this.addBail = addBail;
        this.deleteBail = deleteBail;
        this.updateBail = updateBail;
    }

    public boolean isAddBail() {
        return addBail;
    }

    public boolean isDeleteBail() {
        return deleteBail;
    }

    public boolean isUpdateBail() {
        return updateBail;
    }

    public void setAddBail(boolean addBail) {
        this.addBail = addBail;
    }

    public void setDeleteBail(boolean deleteBail) {
        this.deleteBail = deleteBail;
    }

    public void setUpdateBail(boolean updateBail) {
        this.updateBail = updateBail;
    }



}
