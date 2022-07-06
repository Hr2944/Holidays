package com.hrb.holidays.ui.layouts

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

enum class RevealDirection {
    Left,
    Right
}

enum class SideBackdropValue {
    Concealed,
    RevealedLeft,
    RevealedRight
}

@ExperimentalMaterialApi
class SideBackdropScaffoldState(
    initialValue: SideBackdropValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (SideBackdropValue) -> Boolean = { true },
) : SwipeableState<SideBackdropValue>(
    initialValue = initialValue,
    animationSpec = animationSpec,
    confirmStateChange = confirmStateChange
) {
    val isRevealed: Boolean
        get() = isRevealedLeft || isRevealedRight

    val isRevealedLeft: Boolean
        get() = currentValue == SideBackdropValue.RevealedLeft
    val isRevealedRight: Boolean
        get() = currentValue == SideBackdropValue.RevealedRight

    val isConcealed: Boolean
        get() = currentValue == SideBackdropValue.Concealed

    suspend fun revealLeft() = animateTo(targetValue = SideBackdropValue.RevealedLeft)
    suspend fun revealRight() = animateTo(targetValue = SideBackdropValue.RevealedRight)

    suspend fun conceal() = animateTo(targetValue = SideBackdropValue.Concealed)

    companion object {
        fun Saver(
            animationSpec: AnimationSpec<Float>,
            confirmStateChange: (SideBackdropValue) -> Boolean,
        ): Saver<SideBackdropScaffoldState, *> = Saver(
            save = { it.currentValue },
            restore = {
                SideBackdropScaffoldState(
                    initialValue = it,
                    animationSpec = animationSpec,
                    confirmStateChange = confirmStateChange,
                )
            }
        )
    }
}

@Composable
@ExperimentalMaterialApi
fun rememberSideBackdropScaffoldState(
    initialValue: SideBackdropValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (SideBackdropValue) -> Boolean = { true },
): SideBackdropScaffoldState {
    return rememberSaveable(
        animationSpec,
        confirmStateChange,
        saver = SideBackdropScaffoldState.Saver(
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange,
        )
    ) {
        SideBackdropScaffoldState(
            initialValue = initialValue,
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange,
        )
    }
}

object SideBackdropScaffoldDefaults {
    @Composable
    fun getFrontLayerShape(directions: Set<RevealDirection>, cornerSize: Dp): CornerBasedShape {
        return when {
            RevealDirection.Right in directions && RevealDirection.Left in directions ->
                RoundedCornerShape(cornerSize)
            RevealDirection.Left in directions ->
                RoundedCornerShape(
                    topStart = cornerSize,
                    topEnd = 0.dp,
                    bottomStart = cornerSize,
                    bottomEnd = 0.dp
                )
            RevealDirection.Right in directions ->
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = cornerSize,
                    bottomStart = 0.dp,
                    bottomEnd = cornerSize
                )
            else -> RoundedCornerShape(0.dp)
        }
    }

    val frontLayerElevation: Dp = 5.dp

    val frontLayerScrimColor: Color
        @Composable
        get() = MaterialTheme.colors.surface.copy(alpha = 0.60f)
}

@ExperimentalMaterialApi
@Composable
fun SideBackdrop(
    backLayerContent: @Composable () -> Unit,
    frontLayerContent: @Composable () -> Unit,
    scaffoldState: SideBackdropScaffoldState = rememberSideBackdropScaffoldState(SideBackdropValue.Concealed),
    gesturesEnabled: Boolean = true,
    frontLayerScrimColor: Color = SideBackdropScaffoldDefaults.frontLayerScrimColor,
    frontLayerElevation: Dp = SideBackdropScaffoldDefaults.frontLayerElevation,
    directions: Set<RevealDirection> = setOf(RevealDirection.Left, RevealDirection.Right),
    frontLayerCornerSize: Dp = 8.dp
) {
    BoxWithConstraints {
        val scope = rememberCoroutineScope()

        val width = 3 * constraints.maxWidth.toFloat() / 5

        val anchors = mutableMapOf(0f to SideBackdropValue.Concealed)
        if (RevealDirection.Left in directions) {
            anchors += width to SideBackdropValue.RevealedLeft
        }
        if (RevealDirection.Right in directions) {
            anchors += -width to SideBackdropValue.RevealedRight
        }

        val minFactor =
            if (RevealDirection.Right in directions) SwipeableDefaults.StandardResistanceFactor
            else 0f
        val maxFactor =
            if (RevealDirection.Left in directions) SwipeableDefaults.StandardResistanceFactor
            else 0f

        Box(
            Modifier.swipeable(
                state = scaffoldState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
                reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl,
                resistance = ResistanceConfig(
                    basis = width,
                    factorAtMin = minFactor,
                    factorAtMax = maxFactor
                )
            )
        ) {
            // Back layer
            Surface(
                modifier = Modifier.matchParentSize()
            ) {
                backLayerContent()
                Scrim(
                    alpha = (1 - (scaffoldState.offset.value.absoluteValue / width)).absoluteValue,
                    color = frontLayerScrimColor,
                    onDismiss = {
                        if (gesturesEnabled) {
                            scope.launch { scaffoldState.conceal() }
                        }
                    },
                    visible = scaffoldState.isConcealed
                )
            }

            // Front layer
            val elevation by animateDpAsState(
                targetValue = if (scaffoldState.isRevealed && scaffoldState.progress.fraction == 1f) 0.dp else frontLayerElevation
            )
            val cornerDpShape by animateDpAsState(
                targetValue = if (scaffoldState.isRevealed || scaffoldState.progress.fraction != 1f) frontLayerCornerSize else 0.dp
            )
            Surface(
                elevation = elevation,
                shape = SideBackdropScaffoldDefaults.getFrontLayerShape(
                    directions = directions,
                    cornerSize = cornerDpShape
                ),
                modifier = Modifier.offset {
                    IntOffset(scaffoldState.offset.value.roundToInt(), 0)
                }
            ) {
                val alpha by animateFloatAsState(
                    targetValue = if (scaffoldState.isRevealed && scaffoldState.progress.fraction == 1f) 1f else 0f
                )
                frontLayerContent()
                Scrim(
                    alpha = alpha,
                    color = frontLayerScrimColor,
                    onDismiss = {
                        if (gesturesEnabled) {
                            scope.launch { scaffoldState.conceal() }
                        }
                    },
                    visible = scaffoldState.isRevealed
                )
            }
        }
    }
}

@Composable
private fun Scrim(
    color: Color,
    onDismiss: () -> Unit,
    visible: Boolean,
    alpha: Float
) {
    val pointerModifier =
        if (visible) Modifier.pointerInput(Unit) { detectTapGestures { onDismiss() } }
        else Modifier

    Canvas(
        Modifier
            .fillMaxSize()
            .then(pointerModifier)
    ) {
        drawRect(color = color, alpha = alpha)
    }
}
