package com.example.booking_team22.fragments.accomodation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_team22.R;
import com.example.booking_team22.databinding.FragmentAccomodationPageBinding;
import com.example.booking_team22.fragments.FragmentTransition;
import com.example.booking_team22.model.Accomodation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestAccomodationPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestAccomodationPageFragment extends Fragment {

    public static ArrayList<Accomodation> products = new ArrayList<Accomodation>();
    //private ProductsPageViewModel productsViewModel;
    private FragmentAccomodationPageBinding binding;

    public static GuestAccomodationPageFragment newInstance() {
        return new GuestAccomodationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        products = new ArrayList<Accomodation>();
        binding = FragmentAccomodationPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareProductList(products);

        FragmentTransition.to(GuestAccomodationListFragment.newInstance(products), getActivity(), false, R.id.scroll_products_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareProductList(ArrayList<Accomodation> products){
        products.add(new Accomodation(
                1L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap1));
        products.add(new Accomodation(
                2L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap2));
        products.add(new Accomodation(
                3L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap4));
        products.add(new Accomodation(
                4L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap5));
        products.add(new Accomodation(
                5L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap6));
    }
}