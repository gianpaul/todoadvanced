package pe.com.gianpaul.todo.ui.outstandgroup.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import pe.com.gianpaul.todo.R;

public class EditDialog extends DialogFragment implements View.OnClickListener {
    private TextView saveTextView;
    private TextView cancelTextView;
    private EditText titleEditText;
    private View view;
    private String title;

    public EditDialog(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_edit, container);
        initializeView();
        return view;
    }

    private void initializeView() {
        saveTextView = view.findViewById(R.id.saveTextView);
        cancelTextView = view.findViewById(R.id.cancelTextView);
        titleEditText = view.findViewById(R.id.titleEditText);
        titleEditText.setText(title);
        saveTextView.setOnClickListener(this);
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }

    public interface Callback {
        void acceptEdit(String text);
    }

    @Override
    public void onClick(View view) {
        Callback callback = null;
        try {
            callback = (Callback) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(this.getClass().getSimpleName(), "Callback of this class must be implemented by target fragment!", e);
            throw e;
        }

        if (callback != null) {
            if (view == saveTextView) {
                callback.acceptEdit(titleEditText.getText().toString());
                this.dismiss();
            }
        }
    }
}
