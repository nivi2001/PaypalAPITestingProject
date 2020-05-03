package com.paypal.APITesting.pojo;

public class UpdateProduct {
         private String op;
		private String path;
		private String value;
		
		public UpdateProduct(String op,  String path,String value ) {
			this.op = op;
			this.path = path;
			this.value = value;
		}

		public String getOp() {
			return op;
		}

		public void setOp(String op) {
			this.op = op;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		

}
