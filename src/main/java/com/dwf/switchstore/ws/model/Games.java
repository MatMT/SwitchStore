package com.dwf.switchstore.ws.model;

/**
 * The Games class represents a game entity with properties such as id, title, genre,
 * created_at, and updated_at. It provides getter and setter methods for these properties.
 */
public class Games {

    private int id; // Unique identifier for the game
    private String title; // Title of the game
    private String genre; // Genre of the game
    private Double price; // Price of the game
    private String created_at; // Timestamp when the game was created
    private String updated_at; // Timestamp when the game was last updated

    /**
     * Gets the unique identifier for the game.
     *
     * @return the id of the game
     */
    public int getId() { return id; }

    /**
     * Sets the unique identifier for the game.
     *
     * @param id the id to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * Gets the title of the game.
     *
     * @return the title of the game
     */
    public String getTitle() { return title; }

    /**
     * Sets the title of the game.
     *
     * @param title the title to set
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Gets the genre of the game.
     *
     * @return the genre of the game
     */
    public String getGenre() { return genre; }

    /**
     * Sets the genre of the game.
     *
     * @param genre the genre to set
     */
    public void setGenre(String genre) { this.genre = genre; }

    /**
     * Gets the price of the game.
     *
     * @return the price of the game
     */
    public Double getPrice() { return price; }

    /**
     * Sets the price of the game.
     *
     * @param price the price to set
     */
    public void setPrice(Double price) { this.price = price; }

    /**
     * Gets the timestamp when the game was created.
     *
     * @return the created_at timestamp
     */
    public String getCreated_at() { return created_at; }

    /**
     * Sets the timestamp when the game was created.
     *
     * @param created_at the created_at timestamp to set
     */
    public void setCreated_at(String created_at) { this.created_at = created_at; }

    /**
     * Gets the timestamp when the game was last updated.
     *
     * @return the updated_at timestamp
     */
    public String getUpdated_at() { return updated_at; }

    /**
     * Sets the timestamp when the game was last updated.
     *
     * @param updated_at the updated_at timestamp to set
     */
    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }

    /**
     * Validates the game object by checking if all required fields are not empty.
     *
     * @return true if the game object is valid, false otherwise.
     */
    public boolean isValid() {
        return title != null && !title.trim().isEmpty()
                && genre != null && !genre.trim().isEmpty()
                && price >= 0;
    }

}