org_vaadin_blog_JsLabel = function() {
    var e = this.getElement();

    this.onStateChange = function() {
        e.innerHTML = this.getState().xhtml;
    }
}