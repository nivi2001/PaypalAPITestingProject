package com.paypal.APITesting.pojo;

public class UpdateOrderTestPaypal {
        private String op;
		private String path;
		private Value value;
		
		public UpdateOrderTestPaypal(String op, String path, Value value) {
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

		public Value getValue() {
			return value;
		}

		public void setValue(Value value) {
			this.value = value;
		}
	}


