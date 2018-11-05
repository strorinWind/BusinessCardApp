package ru.strorin.businesscardapp.news;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ru.strorin.businesscardapp.NewsLoader;
import ru.strorin.businesscardapp.R;
import ru.strorin.businesscardapp.about.AboutActivity;
import ru.strorin.businesscardapp.data.network.DefaultResponse;
import ru.strorin.businesscardapp.data.network.dto.NewsDTO;
import ru.strorin.businesscardapp.details.NewsDetailsActivity;


public class NewListActivity extends AppCompatActivity {

    private static final String TAG = NewListActivity.class.getCanonicalName();
    private static final int LAYOUT = R.layout.activity_new_list;
    private static final String NEWS_LIST = "NEWS_LIST";
    private static final String NEWS_LOADED = "NEWS_LOADED";

    private RecyclerView rvNews;
    private NewsItemRecyclerAdapter newsAdapter;
    private Button btnTryAgain;
    private View viewError;
    private View viewLoading;
    private View viewNoData;
    private TextView sectionChooser;
    private TextView tvError;
    private AlertDialog chooseDialog;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private List<NewsDTO> news = new ArrayList<>();

    private final NewsItemRecyclerAdapter.OnItemClickListener clickListener = newsItem -> {
        NewsDetailsActivity.start(this, newsItem);
    };

    public static void start(Activity activity){
        Intent newsDetailsActivityIntent = new Intent(activity, NewListActivity.class);
        activity.startActivity(newsDetailsActivityIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        setupUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupUx();
        loadNews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindUx();
        compositeDisposable.clear();
    }

    private void unbindUx() {
        btnTryAgain.setOnClickListener(null);
    }

    private void setupUi() {
        findViews();
        setupRecyclerViews();
        setupSectionChooser();
    }

    private void setupUx() {
        btnTryAgain.setOnClickListener(v -> onClickTryAgain());
        sectionChooser.setOnClickListener(v -> onClickChooseSection());
    }

    private void onClickTryAgain() {
        loadNews();
    }

    private void onClickChooseSection(){

        ArrayList<String> items = new ArrayList<>();
        for (Section item : Section.values()) {
            items.add(item.name());
        }
        CharSequence[] lst = items.toArray(new CharSequence[items.size()]);

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select news section");
        builder.setSingleChoiceItems(lst, -1, (dialog, item) -> {
            sectionChooser.setText((Section.values()[item]).name());
            chooseDialog.dismiss();
            loadNews();
        });
        chooseDialog = builder.create();
        chooseDialog.show();
    }

    private void loadNews(){
        showState(State.Loading);
        final Disposable searchDisposable = NewsLoader.getInstance()
                .news()
                .topStories(sectionChooser.getText().toString().toLowerCase())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::checkResponseAndShowState, this::handleError);
        compositeDisposable.add(searchDisposable);
    }

    private void handleError(Throwable throwable) {
        if (throwable instanceof IOException) {
            showState(State.NetworkError);
            return;
        }
        showState(State.ServerError);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkResponseAndShowState(@NonNull Response<DefaultResponse<List<NewsDTO>>> response) {
        //Here I use Guard Clauses. You can find more here:
        //https://refactoring.com/catalog/replaceNestedConditionalWithGuardClauses.html

        //Here we have 4 clauses:

        if (!response.isSuccessful()) {
            showState(State.ServerError);
            return;
        }

        final DefaultResponse<List<NewsDTO>> body = response.body();
        if (body == null) {
            showState(State.HasNoData);
            return;
        }

        final List<NewsDTO> data = body.getData();
        if (data == null) {
            showState(State.HasNoData);
            return;
        }

        if (data.isEmpty()) {
            showState(State.HasNoData);
            return;
        }

        newsAdapter.setDataset(data);
        showState(State.HasData);
    }

    public void showState(@NonNull State state) {
        switch (state) {
            case HasData:
                viewError.setVisibility(View.GONE);
                viewLoading.setVisibility(View.GONE);
                viewNoData.setVisibility(View.GONE);

                rvNews.setVisibility(View.VISIBLE);
                break;

            case HasNoData:
                rvNews.setVisibility(View.GONE);
                viewLoading.setVisibility(View.GONE);

                viewError.setVisibility(View.VISIBLE);
                viewNoData.setVisibility(View.VISIBLE);
                break;
            case NetworkError:
                rvNews.setVisibility(View.GONE);
                viewLoading.setVisibility(View.GONE);
                viewNoData.setVisibility(View.GONE);

                tvError.setText(getText(R.string.error_network));
                viewError.setVisibility(View.VISIBLE);
                break;

            case ServerError:
                rvNews.setVisibility(View.GONE);
                viewLoading.setVisibility(View.GONE);
                viewNoData.setVisibility(View.GONE);

                tvError.setText(getText(R.string.error_server));
                viewError.setVisibility(View.VISIBLE);
                break;
            case Loading:
                viewError.setVisibility(View.GONE);
                rvNews.setVisibility(View.GONE);
                viewNoData.setVisibility(View.GONE);

                viewLoading.setVisibility(View.VISIBLE);
                break;

            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }
    }

    private void setupRecyclerViews() {
        newsAdapter = new NewsItemRecyclerAdapter(this, clickListener);

        setRecyclerViewDecoration(rvNews);
        rvNews.setAdapter(newsAdapter);
    }

    private void setRecyclerViewDecoration(RecyclerView recyclerView){
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }
        else {
            int numColumns = 2;
            recyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));
        }
    }

    private void setupSectionChooser() {
        sectionChooser.setText(Section.Home.toString());
    }

    private void findViews() {
        rvNews = findViewById(R.id.rv_news);
        btnTryAgain = findViewById(R.id.btn_try_again);
        viewError = findViewById(R.id.lt_error);
        viewLoading = findViewById(R.id.lt_loading);
        viewNoData = findViewById(R.id.lt_no_data);
        tvError = findViewById(R.id.tv_error);
        sectionChooser = findViewById(R.id.section_chooser);
    }
}
