
package com.example.vangtichai;

import android.app.Activity;
import android.os.Bundle;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.*;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends Activity {

 private static final String STATE = "current_amount";
 private static final int[] D = {500, 100, 50, 20, 10, 5, 2, 1};

 private static final int GREEN = Color.rgb(7, 91, 70);
 private static final int DARK = Color.rgb(20, 37, 31);
 private static final int MUTED = Color.rgb(82, 102, 94);
 private static final int BG = Color.rgb(243, 247, 245);

 private String amount = "0";
 private TextView amountView, totalView;
 private LinearLayout rows;

 private int dp(float n) {
  return (int) (n * getResources().getDisplayMetrics().density + 0.5f);
 }

 @Override
 protected void onCreate(Bundle b) {
  super.onCreate(b);

  if (b != null) {
   amount = b.getString(STATE, "0");
  }

  build();
 }

 @Override
 protected void onSaveInstanceState(Bundle b) {
  b.putString(STATE, amount);
  super.onSaveInstanceState(b);
 }

 private GradientDrawable shape(int color, int radius) {
  GradientDrawable d = new GradientDrawable();
  d.setColor(color);
  d.setCornerRadius(dp(radius));
  return d;
 }

 private TextView txt(String s, int size, int color, boolean bold) {
  TextView t = new TextView(this);
  t.setText(s);
  t.setTextSize(size);
  t.setTextColor(color);

  if (bold) {
   t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
  }

  return t;
 }

 private LinearLayout col() {
  LinearLayout l = new LinearLayout(this);
  l.setOrientation(LinearLayout.VERTICAL);
  return l;
 }

 private void build() {

  boolean land = getResources().getConfiguration().orientation
          == Configuration.ORIENTATION_LANDSCAPE;

  LinearLayout root = col();
  root.setPadding(dp(12), dp(6), dp(12), dp(8));
  root.setBackgroundColor(BG);
  setContentView(root);

  // Header
  LinearLayout head = new LinearLayout(this);
  head.setGravity(Gravity.CENTER_VERTICAL);

  head.addView(
          txt("VangtiChai", 21, DARK, true),
          new LinearLayout.LayoutParams(0, -2, 1)
  );

  TextView tag = txt("SMART CHANGE • BDT", 10, GREEN, true);
  head.addView(tag);
  root.addView(head);

  // Amount card
  LinearLayout card = col();
  card.setPadding(dp(16), dp(9), dp(16), dp(9));
  card.setBackground(shape(GREEN, 18));

  LinearLayout.LayoutParams cp =
          new LinearLayout.LayoutParams(-1, -2);
  cp.topMargin = dp(7);
  root.addView(card, cp);

  card.addView(txt("YOUR AMOUNT", 10, Color.WHITE, true));

  amountView = txt("", land ? 25 : 30, Color.WHITE, true);
  card.addView(amountView);

  LinearLayout summary = new LinearLayout(this);
  summary.setGravity(Gravity.CENTER_VERTICAL);

  summary.addView(
          txt("TOTAL NOTES & COINS", 9, Color.WHITE, true),
          new LinearLayout.LayoutParams(0, -2, 1)
  );

  totalView = txt("0", 12, GREEN, true);
  totalView.setPadding(dp(10), dp(3), dp(10), dp(3));
  totalView.setBackground(shape(Color.WHITE, 20));
  summary.addView(totalView);

  card.addView(summary);

  // Main workspace
  LinearLayout work = new LinearLayout(this);
  work.setOrientation(land
          ? LinearLayout.HORIZONTAL
          : LinearLayout.VERTICAL);

  LinearLayout.LayoutParams wp =
          new LinearLayout.LayoutParams(-1, 0, 1);
  wp.topMargin = dp(8);
  root.addView(work, wp);

  // Breakdown panel
  LinearLayout left = col();
  left.setPadding(dp(9), dp(7), dp(9), dp(6));
  left.setBackground(shape(Color.WHITE, 16));

  LinearLayout.LayoutParams lp = land
          ? new LinearLayout.LayoutParams(0, -1, 1)
          : new LinearLayout.LayoutParams(-1, 0, 1);

  if (land) {
   lp.rightMargin = dp(7);
  } else {
   lp.bottomMargin = dp(7);
  }

  work.addView(left, lp);

  left.addView(txt("Change breakdown", 14, DARK, true));

  // No ScrollView: all eight rows share available space.
  rows = col();
  LinearLayout.LayoutParams rowsParams =
          new LinearLayout.LayoutParams(-1, 0, 1);
  rowsParams.topMargin = dp(4);
  left.addView(rows, rowsParams);

  // Keypad panel
  LinearLayout keysCard = col();
  keysCard.setPadding(dp(9), dp(7), dp(9), dp(7));
  keysCard.setBackground(shape(Color.WHITE, 16));

  LinearLayout.LayoutParams kp = land
          ? new LinearLayout.LayoutParams(0, -1, 1)
          : new LinearLayout.LayoutParams(-1, 0, 1);

  work.addView(keysCard, kp);

  keysCard.addView(txt("Enter amount", 14, DARK, true));

  String[][] keys = {
          {"1", "2", "3"},
          {"4", "5", "6"},
          {"7", "8", "9"},
          {"C", "0", "⌫"}
  };

  LinearLayout grid = col();

  LinearLayout.LayoutParams gp =
          new LinearLayout.LayoutParams(-1, 0, 1);
  gp.topMargin = dp(4);
  keysCard.addView(grid, gp);

  for (String[] line : keys) {

   LinearLayout row = new LinearLayout(this);

   LinearLayout.LayoutParams rp =
           new LinearLayout.LayoutParams(-1, 0, 1);
   rp.bottomMargin = dp(2);

   grid.addView(row, rp);

   for (String k : line) {

    Button button = new Button(this);
    button.setText(k);
    button.setTextSize(land ? 15 : 17);
    button.setAllCaps(false);
    button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    button.setTextColor(k.equals("C") ? Color.WHITE : DARK);

    button.setBackgroundResource(
            k.equals("C")
                    ? R.drawable.key_ripple_primary
                    : R.drawable.key_ripple
    );

    button.setStateListAnimator(null);
    button.setPadding(0, 0, 0, 0);
    button.setMinHeight(0);
    button.setMinimumHeight(0);
    button.setMinWidth(0);
    button.setMinimumWidth(0);

    LinearLayout.LayoutParams bp =
            new LinearLayout.LayoutParams(0, -1, 1);
    bp.setMargins(dp(2), 0, dp(2), 0);

    row.addView(button, bp);
    button.setOnClickListener(v -> key(k));
   }
  }

  render();
 }

 private void key(String k) {

  if (k.equals("C")) {
   amount = "0";

  } else if (k.equals("⌫")) {
   amount = amount.length() <= 1
           ? "0"
           : amount.substring(0, amount.length() - 1);

  } else if (amount.length() < 12) {
   amount = amount.equals("0") ? k : amount + k;
  }

  render();
 }

 private void render() {

  long value = Long.parseLong(amount);

  NumberFormat f =
          NumberFormat.getIntegerInstance(Locale.US);

  amountView.setText("৳ " + f.format(value));

  long rem = value;
  int total = 0;

  rows.removeAllViews();

  for (int den : D) {

   int count = (int) (rem / den);
   rem %= den;
   total += count;

   LinearLayout item = new LinearLayout(this);
   item.setGravity(Gravity.CENTER_VERTICAL);
   item.setPadding(dp(5), 0, dp(5), 0);
   item.setBackground(shape(BG, 7));

   LinearLayout.LayoutParams ip =
           new LinearLayout.LayoutParams(-1, 0, 1);
   ip.bottomMargin = dp(2);

   rows.addView(item, ip);

   TextView denomination =
           txt("৳" + den, 12, DARK, true);

   item.addView(
           denomination,
           new LinearLayout.LayoutParams(0, -2, 1)
   );

   TextView result = txt(
           "× " + count + "   ৳" + f.format((long) count * den),
           11,
           count > 0 ? GREEN : MUTED,
           true
   );

   result.setGravity(Gravity.CENTER_VERTICAL);
   item.addView(result);
  }

  totalView.setText(String.valueOf(total));
 }
}
