package com.fake.api;

// This is the product pojo class and rating is not child of product class. Rating is the part of product class.
//POJO class
public class Product {
	
	private int id;
	private String title;
	private double price;
	private String description;
	private String category;
	private String image;
	private Rating rating;  // Class data type. create Rating class reference veriable. rating is the class veriable of Product class. 
	// and data type of Rating class is non premitive 
	// so pojo is non static for all above veriable except the inner class

	// default constructor
	public Product() {
	}

	// Parameterize constructor
	public Product(int id, String title, double price, String description, String category, String image,
			Rating rating) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.description = description;
		this.category = category;
		this.image = image;
		this.rating = rating;
	}
// Getter and Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	// Rating class: Inner class
	public static class Rating {
		private double rate;
		private int count;

		public Rating() {
		}

		public Rating(double rate, int count) {
			this.rate = rate;
			this.count = count;
		}

		public double getRate() {
			return rate;
		}

		public void setRate(double rate) {
			this.rate = rate;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	}


}
