package ru.mctitunes.ui.presenters;

class BasePresenter<View> {

    protected View view;

    public void bindView(View view) {
        final View previousView = this.view;

        if (previousView != null)
            throw new IllegalStateException("Previous view is not unbounded! previousView = " + previousView);
        this.view = view;
    }

    public void unbindView(View view) {
        final View previousView = this.view;

        if (previousView == view)
            this.view = null;
        else
            throw new IllegalStateException("Unexpected view! previousView = " + previousView + ", view to unbind = " + view);
    }
}
