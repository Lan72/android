package mega.privacy.android.app.components.twemoji;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.CallSuper;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;

import mega.privacy.android.app.R;
import mega.privacy.android.app.lollipop.ManagerActivityLollipop;
import mega.privacy.android.app.lollipop.megachat.ArchivedChatsActivity;
import mega.privacy.android.app.lollipop.megachat.ChatActivityLollipop;
import mega.privacy.android.app.lollipop.megachat.ChatExplorerActivity;
import mega.privacy.android.app.lollipop.megachat.GroupChatInfoActivityLollipop;
import mega.privacy.android.app.lollipop.megachat.calls.ChatCallActivity;
import mega.privacy.android.app.utils.Util;

public class EmojiTextView extends AppCompatTextView implements EmojiTexViewInterface{

  private float emojiSize;
  public static final int LAST_MESSAGE_TEXTVIEW_WIDTH_PORTRAIT = 190;
    public static final int TITLE_CHAT_TEXTVIEW_WIDTH_LANDSCAPE = 300;

    public static final int LAST_MESSAGE_TEXTVIEW_WIDTH_LANDSCAPE = 260;
  public static final int LAST_MESSAGE_TEXTVIEW_WIDTH_CHAT_EXPLORER = 255;
  public static final int TITLE_TEXTVIEW_RECENT_CHAT = 100;

  private Context mContext;
  private Display display;
  private DisplayMetrics mOutMetrics;
  private int textViewMaxWidth;

  public EmojiTextView(final Context context) {
    this(context, null);
    mContext = context;
  }

  public EmojiTextView(Context context, @Nullable AttributeSet attrs) {
    this(context,attrs, 0);
    mContext = context;
  }

  public EmojiTextView(Context context,@Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    log("EmojiTextView");

    mContext = context;

    if (!isInEditMode()) {
      EmojiManager.getInstance().verifyInstalled();
    }

    final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
    final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;
    if (attrs == null) {
      emojiSize = defaultEmojiSize;
    } else {
      final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmojiTextView);
      try {
        emojiSize = a.getDimension(R.styleable.EmojiTextView_emojiSize, defaultEmojiSize);
      } finally {
        a.recycle();
      }
    }
    if(mContext!=null){
        if(mContext instanceof GroupChatInfoActivityLollipop){
            //Title chat in group info:
            log("EmojiTextView: GroupChatInfoActivityLollipop: ");
            display = ((GroupChatInfoActivityLollipop)mContext).getWindowManager().getDefaultDisplay();
            mOutMetrics = new DisplayMetrics ();
            display.getMetrics(mOutMetrics);

            if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                textViewMaxWidth = Util.scaleWidthPx(TITLE_CHAT_TEXTVIEW_WIDTH_LANDSCAPE, mOutMetrics);
            }else{
                textViewMaxWidth = Util.scaleWidthPx(LAST_MESSAGE_TEXTVIEW_WIDTH_PORTRAIT, mOutMetrics);
            }
            setText(getText());

        }else if (mContext instanceof ManagerActivityLollipop) {
            log("EmojiTextView: ManagerActivityLollipop: ");

            display = ((ManagerActivityLollipop)mContext).getWindowManager().getDefaultDisplay();
            mOutMetrics = new DisplayMetrics ();
            display.getMetrics(mOutMetrics);
            if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                textViewMaxWidth = Util.px2dp(LAST_MESSAGE_TEXTVIEW_WIDTH_LANDSCAPE, mOutMetrics);
            }else{
                textViewMaxWidth = Util.px2dp(LAST_MESSAGE_TEXTVIEW_WIDTH_PORTRAIT, mOutMetrics);
            }
            setText(getText());

        }else if(mContext instanceof ChatExplorerActivity){
            log("EmojiTextView: ChatExplorerActivity: ");

            display = ((ChatExplorerActivity)mContext).getWindowManager().getDefaultDisplay();
            mOutMetrics = new DisplayMetrics ();
            display.getMetrics(mOutMetrics);
            textViewMaxWidth = Util.scaleWidthPx(LAST_MESSAGE_TEXTVIEW_WIDTH_CHAT_EXPLORER, mOutMetrics);
            setText(getText());

        }else if(mContext instanceof ArchivedChatsActivity){
            log("EmojiTextView: ArchivedChatsActivity: ");

            display = ((ArchivedChatsActivity)mContext).getWindowManager().getDefaultDisplay();
            mOutMetrics = new DisplayMetrics ();
            display.getMetrics(mOutMetrics);
            textViewMaxWidth = Util.scaleWidthPx(LAST_MESSAGE_TEXTVIEW_WIDTH_PORTRAIT, mOutMetrics);
            setText(getText());

        }else if(mContext instanceof ChatCallActivity){
            log("EmojiTextView: ChatCallActivity: ");

            display = ((ChatCallActivity)mContext).getWindowManager().getDefaultDisplay();
            mOutMetrics = new DisplayMetrics ();
            display.getMetrics(mOutMetrics);
            textViewMaxWidth = Util.scaleWidthPx(LAST_MESSAGE_TEXTVIEW_WIDTH_PORTRAIT, mOutMetrics);
            setText(getText());

        } else if (mContext instanceof ChatActivityLollipop) {
            log("EmojiTextView: ChatActivityLollipop: ");

            display = ((ChatActivityLollipop)mContext).getWindowManager().getDefaultDisplay();
            mOutMetrics = new DisplayMetrics ();
            display.getMetrics(mOutMetrics);
            textViewMaxWidth = Util.scaleWidthPx(LAST_MESSAGE_TEXTVIEW_WIDTH_PORTRAIT, mOutMetrics);
            setText(getText());

        }else {
            if (mContext instanceof ContextWrapper) {
                Context inContext = ((ContextWrapper) mContext).getBaseContext();
                if (inContext != null) {
                  if (inContext instanceof ChatActivityLollipop) {
                        log("EmojiTextView:ContextWrapper: ChatActivityLollipop: ");
                        display = ((ChatActivityLollipop)inContext).getWindowManager().getDefaultDisplay();
                        mOutMetrics = new DisplayMetrics ();
                        display.getMetrics(mOutMetrics);
                        textViewMaxWidth = Util.scaleWidthPx(LAST_MESSAGE_TEXTVIEW_WIDTH_CHAT_EXPLORER, mOutMetrics);
                        setText(getText());

                  } else if (inContext instanceof ChatCallActivity) {
                        log("EmojiTextView:ContextWrapper: ChatCallActivity: ");
                        display = ((ChatCallActivity)inContext).getWindowManager().getDefaultDisplay();
                        mOutMetrics = new DisplayMetrics ();
                        display.getMetrics(mOutMetrics);
                        textViewMaxWidth = Util.scaleWidthPx(LAST_MESSAGE_TEXTVIEW_WIDTH_CHAT_EXPLORER, mOutMetrics);
                        setText(getText());
                  }
                }
          } else {
                log("EmojiTextView:ContextWrapper: OTHER: ");
                setText(getText());
          }
        }
    }else{
        log("EmojiTextView:OTHER: ");
        setText(getText());

    }

  }
  @Override public void setText(CharSequence rawText, BufferType type) {
    log("setText");

      CharSequence text = rawText == null ? "" : rawText;
      SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
      Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
      float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;
      EmojiManager.getInstance().replaceWithImages(getContext(), spannableStringBuilder, emojiSize, defaultEmojiSize);

      if(mContext!=null){
          if(mContext instanceof GroupChatInfoActivityLollipop){
              //Title chat in group info:
              log("setText: GroupChatInfoActivityLollipop: textViewMaxWidth = "+textViewMaxWidth);
              CharSequence textF = TextUtils.ellipsize(spannableStringBuilder, getPaint(), textViewMaxWidth, TextUtils.TruncateAt.END);
              super.setText(textF, type);

          }else if (mContext instanceof ManagerActivityLollipop) {
              log("setText: ManagerActivityLollipop: ");

              CharSequence textF = TextUtils.ellipsize(spannableStringBuilder, getPaint(), textViewMaxWidth, TextUtils.TruncateAt.END);
              super.setText(textF, type);
          }else if(mContext instanceof ChatExplorerActivity){
              log("setText: ChatExplorerActivity: ");

              CharSequence textF = TextUtils.ellipsize(spannableStringBuilder, getPaint(), textViewMaxWidth, TextUtils.TruncateAt.END);
              super.setText(textF, type);
          }else if(mContext instanceof ArchivedChatsActivity){
              log("setText: ArchivedChatsActivity: ");
              CharSequence textF = TextUtils.ellipsize(spannableStringBuilder, getPaint(), textViewMaxWidth, TextUtils.TruncateAt.END);
              super.setText(textF, type);

          }else if(mContext instanceof ChatCallActivity){
              log("setText: ChatCallActivity: ");
              CharSequence textF = TextUtils.ellipsize(spannableStringBuilder, getPaint(), textViewMaxWidth, TextUtils.TruncateAt.END);
              super.setText(textF, type);

          } else if (mContext instanceof ChatActivityLollipop) {
              log("setText: ChatActivityLollipop: ");
              super.setText(spannableStringBuilder, type);

          }else {
              if (mContext instanceof ContextWrapper) {
                  Context inContext = ((ContextWrapper) mContext).getBaseContext();
                  if (inContext != null) {
                      if (inContext instanceof ChatActivityLollipop) {
                          log("setText:ContextWrapper: ChatActivityLollipop: ");
                          CharSequence textF = TextUtils.ellipsize(spannableStringBuilder, getPaint(), textViewMaxWidth, TextUtils.TruncateAt.END);
                          super.setText(textF, type);

                      } else if (inContext instanceof ChatCallActivity) {
                          log("setText:ContextWrapper: ChatCallActivity: ");
                          CharSequence textF = TextUtils.ellipsize(spannableStringBuilder, getPaint(), textViewMaxWidth, TextUtils.TruncateAt.END);
                          super.setText(textF, type);
                      }
                  }
              } else {
                  log("setText:ContextWrapper: OTHER: "+mContext);
                  super.setText(spannableStringBuilder, type);
              }
          }
      }else{
          log("setText: mContext == NULL ");
          super.setText(spannableStringBuilder, type);
      }

  }

  @Override
  protected void onTextChanged(CharSequence rawText, int start, int lengthBefore, int lengthAfter) {
  }

  @Override @CallSuper public void backspace() {
    final KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
    dispatchKeyEvent(event);
  }
  @Override public float getEmojiSize() {
    return emojiSize;
  }
  @Override public final void setEmojiSize(@Px final int pixels) {
    setEmojiSize(pixels, true);
  }
  @Override public final void setEmojiSize(@Px final int pixels, final boolean shouldInvalidate) {
    emojiSize = pixels;
    if (shouldInvalidate) {
      setText(getText());
    }
  }
  @Override public final void setEmojiSizeRes(@DimenRes final int res) {
    setEmojiSizeRes(res, true);
  }
  @Override public final void setEmojiSizeRes(@DimenRes final int res, final boolean shouldInvalidate) {
    setEmojiSize(getResources().getDimensionPixelSize(res), shouldInvalidate);
  }

  public static void log(String message) {
    Util.log("EmojiTextView", message);
  }
}