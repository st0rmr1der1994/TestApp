package com.my.test.testapp.navigation

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler

fun Router.goBack() = activity?.onBackPressed() ?: throw IllegalStateException("Activity cannot be null at this point")

fun Router.go(controller: Controller) = go(constructTransaction(controller))

fun Router.go(routerTransaction: RouterTransaction) = pushController(routerTransaction)

fun constructTransaction(controller: Controller, pushChangeHandler: ControllerChangeHandler,
                         popChangeHandler: ControllerChangeHandler): RouterTransaction {
    return RouterTransaction.with(controller)
            .pushChangeHandler(pushChangeHandler)
            .popChangeHandler(popChangeHandler)
}

fun constructTransaction(controller: Controller): RouterTransaction =
        constructTransaction(controller, HorizontalChangeHandler(), HorizontalChangeHandler())
