package com.wesuck.codekampkonnect.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("actionable_id")
    @Expose
    private Integer actionableId;
    @SerializedName("actionable_type")
    @Expose
    private String actionableType;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The actionableId
     */
    public Integer getActionableId() {
        return actionableId;
    }

    /**
     *
     * @param actionableId
     * The actionable_id
     */
    public void setActionableId(Integer actionableId) {
        this.actionableId = actionableId;
    }

    /**
     *
     * @return
     * The actionableType
     */
    public String getActionableType() {
        return actionableType;
    }

    /**
     *
     * @param actionableType
     * The actionable_type
     */
    public void setActionableType(String actionableType) {
        this.actionableType = actionableType;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(UpdatedAt updatedAt) {
        this.updatedAt = updatedAt;
    }

}