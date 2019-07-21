package com.atto.android.view.layout;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
  private int spanCount;
  private boolean includeEdge;
  private int spacingHorizontalPixels;
  private int spacingVerticalPixels;

  public SpacesItemDecoration(int spanCount, boolean includeEdge, int spacingHorizontalPixels, int spacingVerticalPixels) {
    this.spanCount = spanCount;
    this.includeEdge = includeEdge;
    this.spacingHorizontalPixels = spacingHorizontalPixels;
    this.spacingVerticalPixels = spacingVerticalPixels;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    int position = parent.getChildAdapterPosition(view); // item position
    int column = position % spanCount; // item column

    if (includeEdge) {
      outRect.left = spacingHorizontalPixels - column * spacingHorizontalPixels / spanCount; // spacing - column * ((1f / spanCount) * spacing)
      outRect.right = (column + 1) * spacingHorizontalPixels / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

      if (position < spanCount) { // top edge
        outRect.top = spacingVerticalPixels;
      }
      outRect.bottom = spacingVerticalPixels; // item bottom
    } else {
      outRect.left = column * spacingHorizontalPixels / spanCount; // column * ((1f / spanCount) * spacing)
      outRect.right = spacingHorizontalPixels - (column + 1) * spacingHorizontalPixels / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
      if (position >= spanCount) {
        outRect.top = spacingVerticalPixels; // item top
      }
    }
  }
}