package it.disim.tlp.webreputation.plugin.model.aggregatorplugin;

import java.util.Date;


public class AggregatorPost {
	
	private String title;
	private String text; // contenuto
	private String author; // autore (utente)
	private Date date; // data
	private int visibility; // visibilita
	private String source; // fonte
	private String link; // url
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getVisibility(){
		return visibility;
	}
	public void setVisibility(int visibility){
		this.visibility = visibility;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
