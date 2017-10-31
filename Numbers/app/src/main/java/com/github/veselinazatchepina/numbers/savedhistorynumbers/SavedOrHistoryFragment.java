package com.github.veselinazatchepina.numbers.savedhistorynumbers;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.veselinazatchepina.numbers.R;
import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.enums.NumbersListType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SavedOrHistoryFragment extends Fragment implements SavedOrHistoryContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private Unbinder unbinder;

    private SavedOrHistoryContract.Presenter mSavedOrHistoryPresenter;
    private CharSequence mTitle;
    private NumbersListType mNumbersListType;

    public SavedOrHistoryFragment() {
    }

    public static SavedOrHistoryFragment newInstance() {
        return new SavedOrHistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved_or_history, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        defineTypeOfNumbersList();
        mSavedOrHistoryPresenter.getNumbersList(mNumbersListType);
        return rootView;
    }

    private void defineTypeOfNumbersList() {
        mTitle = getActivity().getTitle();
        if (mTitle.equals(getString(R.string.menu_history))) {
            mNumbersListType = NumbersListType.HISTORY;
        } else {
            mNumbersListType = NumbersListType.USER;
        }
    }

    @Override
    public void showNumbersList(List<Number> numbers) {
        SavedOrHistoryRecyclerViewAdapter savedOrHistoryRecyclerViewAdapter =
                new SavedOrHistoryRecyclerViewAdapter(numbers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(savedOrHistoryRecyclerViewAdapter);
    }

    @Override
    public void setPresenter(SavedOrHistoryContract.Presenter presenter) {
        mSavedOrHistoryPresenter = presenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.saved_or_history_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_number:
                openDeleteAllNumbersDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDeleteAllNumbersDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View dialogView = layoutInflater.inflate(R.layout.dialog_delete, null);
        TextView deleteDialogTitle = ButterKnife.findById(dialogView, R.id.dialog_delete_title);
        deleteDialogTitle.setText(getResources().getString(R.string.dialog_delete_all_number_title));
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());
        mDialogBuilder.setView(dialogView);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.dialog_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mSavedOrHistoryPresenter.deleteNumbers(mNumbersListType);
                            }
                        })
                .setNegativeButton(getString(R.string.dialog_cancel_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();
        Button nButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(getResources().getColor(R.color.colorAccent));
        Button pButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void showSnackBarDeletedNumber() {
        createSnackBar(getString(R.string.snack_bar_delete_number_text));
    }

    @Override
    public void showSnackBarDeletedAllNumbers() {
        createSnackBar(getString(R.string.snack_bar_delete_all_numbers_text));
    }

    private void createSnackBar(String text) {
        final CoordinatorLayout coordinatorLayout = ButterKnife.findById(getActivity(), R.id.coordinator_layout);
        Snackbar snackbarIsDeleted = Snackbar.make(coordinatorLayout,
                text,
                Snackbar.LENGTH_LONG);
        snackbarIsDeleted.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class SavedOrHistoryRecyclerViewAdapter extends RecyclerView.Adapter<SavedOrHistoryRecyclerViewAdapter.MyViewHolder> {
        private static final int EMPTY_LIST = 0;
        private static final int NOT_EMPTY_LIST = 1;

        private List<Number> mNumbers;

        public SavedOrHistoryRecyclerViewAdapter(List<Number> numbers) {
            mNumbers = numbers;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case EMPTY_LIST:
                    View itemViewEmpty = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.empty_recycler_view_item, parent, false);
                    return new MyViewHolder(itemViewEmpty);
                case NOT_EMPTY_LIST:
                    View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.saved_or_history_recycler_view_item, parent, false);
                    return new MyViewHolder(itemView);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (!mNumbers.isEmpty()) {
                Number currentNumber = mNumbers.get(position);
                holder.mNumberValue.setText(currentNumber.getNumber());
                holder.mNumberDescriptionValue.setText(currentNumber.getText());
                holder.mQueryDateValue.setText(currentNumber.getDate());
                holder.mCurrentNumber = currentNumber;
            }
        }

        @Override
        public int getItemCount() {
            if (mNumbers.isEmpty()) {
                return 1;
            }
            return mNumbers.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mNumbers.isEmpty() ? EMPTY_LIST : NOT_EMPTY_LIST;
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
            Number mCurrentNumber;
            @Nullable
            @BindView(R.id.number_value)
            TextView mNumberValue;
            @Nullable
            @BindView(R.id.number_description_value)
            TextView mNumberDescriptionValue;
            @Nullable
            @BindView(R.id.query_date_value)
            TextView mQueryDateValue;

            MyViewHolder(View container) {
                super(container);
                ButterKnife.bind(this, container);
                container.setOnLongClickListener(this);
            }

            @Override
            public boolean onLongClick(View view) {
                openPopupMenu(view);
                return false;
            }

            private void openPopupMenu(View view) {
                PopupMenu popup = new PopupMenu(getActivity(), view);
                popup.getMenuInflater().inflate(R.menu.share_delete_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.share_number:
                                setShareAction(item);
                                break;
                            case R.id.delete_number:
                                openDeleteNumberDialog();
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }

            private void setShareAction(MenuItem item) {
                ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
                Intent mSharingIntent = new Intent(Intent.ACTION_SEND);
                mSharingIntent.setType("text/plain");
                String numberTextForShareBody = mCurrentNumber.getText();
                mSharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.current_number_share_intent_theme));
                mSharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, numberTextForShareBody);
                if (shareActionProvider != null) {
                    shareActionProvider.setShareIntent(mSharingIntent);
                }

            }

            private void openDeleteNumberDialog() {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View dialogView = layoutInflater.inflate(R.layout.dialog_delete, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());
                mDialogBuilder.setView(dialogView);
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.dialog_ok_button),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mSavedOrHistoryPresenter.deleteNumber(mCurrentNumber, mNumbersListType);
                                    }
                                })
                        .setNegativeButton(getString(R.string.dialog_cancel_button),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();
                Button nButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nButton.setTextColor(getResources().getColor(R.color.colorAccent));
                Button pButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                pButton.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }
    }
}
