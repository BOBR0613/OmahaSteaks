package com.omahasteaks.data.objects;

public class ImageLink {
    private String text = "";
    private String src = "";
    private String href = "";

    public String getSrc() {
	return src;
    }

    public void setSrc(String src) {
	this.src = src;
    }

    public String getHref() {
	return href;
    }

    public void setHref(String href) {
	this.href = href;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public ImageLink(String src, String href, String text) {
	super();
	this.src = src;
	this.href = href;
	this.text = text;
    }

    public ImageLink() {
	super();
    }

    @Override
    public String toString() {
	return "ImageLink [text=" + text + ", src=" + src + ", href=" + href + "]";
    }

    @Override
    public boolean equals(Object imgLink) {
	if (imgLink == null)
	    return false;
	if (!(imgLink instanceof ImageLink))
	    return false;
	if (imgLink == this)
	    return true;
	return (this.src.equals(((ImageLink) imgLink).src) && this.href.equals(((ImageLink) imgLink).href));
    }
}
