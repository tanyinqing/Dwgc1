package com.yzkj.model;



public class usermodel {
	public String _id;
	public String name;
	public String yes;
	public String no;
	public String cause;
	public String question;
	public String photo;
	
	
	@Override
	public String toString() {
		return "usermodel [_id=" + _id + ", name=" + name + ", yes=" + yes
				+ ", no=" + no + ", cause=" + cause + ", question=" + question
				+ ", photo=" + photo + "]";
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public usermodel() {
		super();
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYes() {
		return yes;
	}

	public void setYes(String yes) {
		this.yes = yes;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

}
