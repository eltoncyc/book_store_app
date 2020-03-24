package app.book.data.purchase;


public class Purchase {
	public static final int ATTRIBUTE_COUNT = 4;

	private long id;
	private long customerId;
	private long bookId;
	private float price;
	private String customerLastName;
	private String bookTitle;
	
	public static class Builder {
		// Required parameters
		private long id;
		private long customerId;
		private long bookId;
		private float price;
		private String customerLastName;
		private String bookTitle;

		public Builder(long id, long customerId, long bookId, float price) {
			this.id = id;
			this.customerId = customerId;
			this.bookId = bookId;
			this.price = price;
		}
		
		public Builder setCustomerLastName(String customerLastName) {
			this.customerLastName = customerLastName;
			return this;
		}
		
		public Builder setBookTitle(String bookTitle) {
			this.bookTitle = bookTitle;
			return this;
		}
		
		public Purchase build() {
			return new Purchase(this);
		}
	}

	/**
	 * @param builder
	 */
	public Purchase(Builder builder) {
		this.id = builder.id;
		this.customerId = builder.customerId;
		this.bookId = builder.bookId;
		this.price = builder.price;
		this.customerLastName = builder.customerLastName;
		this.bookTitle = builder.bookTitle;
	}

	/**
	 * @return the attributeCount
	 */
	public static int getAttributeCount() {
		return ATTRIBUTE_COUNT;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the customerId
	 */
	public long getCustomerId() {
		return customerId;
	}

	/**
	 * @return the bookid
	 */
	public long getBookId() {
		return bookId;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	
	
	
	/**
	 * @return the customerLastName
	 */
	public String getCustomerLastName() {
		return customerLastName;
	}


	/**
	 * @return the bookTitle
	 */
	public String getBookTitle() {
		return bookTitle;
	}



	@Override
	public String toString() {
		return "Purchase [id=" + id + ", customerId=" + customerId + ", bookId=" + bookId + ", price=" + price
				+ ", customerLastName=" + customerLastName + ", bookTitle=" + bookTitle + "]";
	}

}
