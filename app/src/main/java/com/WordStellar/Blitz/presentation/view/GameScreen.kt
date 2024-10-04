package com.WordStellar.Blitz.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.WordStellar.Blitz.R
import com.WordStellar.Blitz.data.Prefs
import com.WordStellar.Blitz.data.SoundManager
import com.WordStellar.Blitz.domain.levels
import com.WordStellar.Blitz.presentation.navigation.OutlinedText
import com.WordStellar.Blitz.ui.theme.WordStellarBlitzTheme
import com.WordStellar.Blitz.ui.theme.myfont
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.roundToInt


data class SmartChar(
    val char: Char,
    val right: Boolean,
    val index: Int
)


@Composable
fun DraggableLetter(
    modifier: Modifier = Modifier,
    letter: SmartChar,
) {
    Box(
        modifier = modifier
    ) {
        OutlinedText(
            text = letter.char.toString(),
            fillColor = Color(0xffFEFFA4),
            outlineColor = Color(0xffCB1218),
            fontSize = 36.sp
        )
    }
}

@Composable
fun GameScreen(
    level: Int = Prefs.level,
    onResult: (Boolean, Int) -> Unit,
   // onComplete: () -> Unit,
    onRetry: () -> Unit
) {
    var droppedIndexes by remember { mutableStateOf(listOf<Int>()) }
    val word: String = levels[if (level > levels.size) levels.size - 1 else level - 1]
    var timer by remember { mutableIntStateOf(0) }
    var letters by remember {
        mutableStateOf(
            word.toList().mapIndexed { index, c -> SmartChar(c, false, index) }.shuffled()
        )
    }
    var selectedLetters by remember { mutableStateOf(listOf<SmartChar>()) }
    var blockUi by remember { mutableStateOf(false) }
    var isSettings by remember { mutableStateOf(false) }
    var isPause by remember { mutableStateOf(false) }
    val fieldPositions by remember { mutableStateOf(mutableMapOf<Int, Offset>()) }
    var trigger by remember { mutableStateOf(false) }
    // Фиксируем позиции полей для проверки пересечений
    LaunchedEffect(Unit) {
        // Обновление позиций полей после размещения UI
        fieldPositions.clear()
    }
    var flag by remember { mutableStateOf(false) }
    LaunchedEffect(trigger) {
        if (flag) {
            return@LaunchedEffect
        }
        if (selectedLetters.any { !it.right }) {
            flag = true
            blockUi = true
            delay(1000L)
            droppedIndexes.lastOrNull()?.let {
                droppedIndexes = droppedIndexes - it
            }
            if (!letters.contains(selectedLetters.first { !it.right })) {
                letters = letters + selectedLetters.first { !it.right }
            }
            selectedLetters = selectedLetters.filter { it.right }.toMutableList()
            blockUi = false
            flag = false
        } else {
            blockUi = true
            delay(1100)
            blockUi = false
        }
    }

    LaunchedEffect(timer) {

        delay(1000L)
        timer += 1

//        blockUi = true
    }

    if (isSettings) {
        BackHandler { isSettings = false }
        OptionsScreen { isSettings = false }
    } else if (isPause) {
        BackHandler { isPause = false }
        PauseScreen(onRetry = onRetry, onOption = { isSettings = true }) { isPause = false }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(painterResource(id = R.drawable.mainbg), contentScale = ContentScale.Crop),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Верхняя панель с кнопками
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "Menu",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { isPause = true }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Settings",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { isSettings = true }
                    )
                }

                // Уровень
                Box(
                    modifier = Modifier
                        .size(150.dp, 50.dp),

                    contentAlignment = Alignment.Center
                ) {
                    OutlinedText(
                        text = "LEVEL ${if (level > levels.size) levels.size else level}",
                        outlineColor = Color(0xFF2176C7),
                        fillColor = Color.White,
                        fontSize = 24.sp
                    )

                }
                Box(
                    modifier = Modifier
                        .size(150.dp, 50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedText(
                        text = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            timer / 60,
                            timer % 60
                        ),
                        outlineColor = Color(0xFF2176C7),
                        fillColor = Color.White,
                        fontSize = 24.sp
                    )

                }

                Spacer(modifier = Modifier.height(26.dp))

                // Поле для отображения правильных букв
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    word.forEachIndexed { index, letter ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .onGloballyPositioned { coordinates ->
                                    fieldPositions[index] = coordinates.positionInRoot()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            val enteredWord = selectedLetters.getOrNull(index)

                            Image(
                                painter = painterResource(
                                    id = if (enteredWord == null) R.drawable.poledlyabukv else if (enteredWord.right) R.drawable.right_letter else R.drawable.failed_letter
                                ),
                                contentDescription = "Letter box",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )

                            OutlinedText(
                                text = enteredWord?.char?.toString() ?: "",
                                fillColor = Color(0xffFEFFA4),
                                outlineColor = Color(0xffCB1218),
                                fontSize = 24.sp
                            )
                        }
                    }
                }

                // Разбросанные буквы
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        letters.chunked(3).forEach { row ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                row.forEach { letter ->

                                    var offsetX by remember { mutableFloatStateOf(0f) }
                                    var offsetY by remember { mutableFloatStateOf(0f) }
                                    var lastOffset by remember { mutableStateOf(Offset.Zero) }
                                    val dp45InPx = LocalDensity.current.run { 45.dp.toPx() }
                                    DraggableLetter(
                                        letter = letter,
                                        modifier = Modifier
                                            .offset {
                                                IntOffset(
                                                    offsetX.roundToInt(),
                                                    offsetY.roundToInt()
                                                )
                                            }
                                            .then(
                                                if (blockUi) Modifier
                                                    .alpha(0.5f)
                                                else Modifier
                                                    .pointerInput(Unit) {
                                                        detectDragGestures { change, dragAmount ->
                                                            change.consume()
                                                            offsetX += dragAmount.x
                                                            offsetY += dragAmount.y
                                                            if (!blockUi) {
                                                                val targetY =
                                                                    fieldPositions.values.firstOrNull()?.y
                                                                targetY?.let {
                                                                    if (lastOffset.y in (targetY - dp45InPx)..(targetY + dp45InPx)) {
                                                                        offsetX = 0f
                                                                        offsetY = 0f
                                                                        if (selectedLetters.find { it.index == selectedLetters.size } == null) {
                                                                            selectedLetters =
                                                                                selectedLetters + letter.copy(
                                                                                    right = letter.char == word[selectedLetters.size]
                                                                                )
                                                                            droppedIndexes =
                                                                                droppedIndexes + selectedLetters.size
                                                                        }
                                                                        trigger = !trigger

                                                                        letters
                                                                            .firstOrNull { it.char == letter.char }
                                                                            ?.let {
                                                                                letters =
                                                                                    letters - it
                                                                            }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    .onGloballyPositioned { coordinates ->
                                                        lastOffset = coordinates.positionInRoot()
                                                    }
                                                    .clickable {
                                                        if (!blockUi) {
                                                            selectedLetters =
                                                                selectedLetters + letter.copy(
                                                                    right = letter.char == word[selectedLetters.size]
                                                                )
                                                            trigger = !trigger

                                                            letters
                                                                .firstOrNull { it.char == letter.char }
                                                                ?.let {
                                                                    letters = letters - it
                                                                }
                                                        }
                                                    }
                                            ),
                                    )
                                }
                            }
                        }
                    }
                }

                // Таймер и кнопка завершения
                Column(horizontalAlignment = Alignment.CenterHorizontally) {


                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White)
                            .size(200.dp, 60.dp)
                            .clickable {
                                if (selectedLetters.size == word.length && selectedLetters.all { it.right }) {
                                    onResult(true, timer)
                                    Prefs.passLevel()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(190.dp, 50.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2176C7)), // Голубой фон
                            contentAlignment = Alignment.Center
                        ) {
                            OutlinedText(
                                text = "COMPLETE",
                                outlineColor = if (selectedLetters.size == word.length) Color(
                                    0xFF4296BD
                                ) else Color(0xFF59B6D3),
                                fillColor = if (selectedLetters.size == word.length) Color.White else Color.Gray,
                                fontSize = 40.sp
                            )


                        }
                    }
                }
            }
        }
    }
}


