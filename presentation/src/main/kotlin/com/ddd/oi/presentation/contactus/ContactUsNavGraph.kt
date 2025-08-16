package com.ddd.oi.presentation.contactus

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToContactUs() {
    navigate(Route.ContactUs)
}

fun NavGraphBuilder.contactUsNavGraph(
    navigatePopBack: () -> Unit
) {
    composable<Route.ContactUs> {
        ContactUsScreen(
            onBack = navigatePopBack
        )
    }
}