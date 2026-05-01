package com.mathy.contestprep

import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.res.ColorStateList
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs
import com.google.firebase.auth.FirebaseAuth

class numbersGame : AppCompatActivity() {

    // This stores one tested puzzle.
    data class Puzzle(
        val target: Int,
        val numbers: List<Int>
    )

    // For now, these are sample tested puzzles.
    // Later you can expand this array to 5000+ puzzles or load from JSON.
    private val puzzles = listOf(
        Puzzle(100, listOf(50, 25, 10, 5, 2, 1)),
        Puzzle(742, listOf(50, 25, 8, 7, 12, 3)),
        Puzzle(384, listOf(64, 12, 8, 6, 4, 2)),
        Puzzle(999, listOf(100, 50, 25, 9, 3, 2)),
        Puzzle(256, listOf(32, 16, 8, 4, 2, 1)),
        Puzzle(1200, listOf(100, 75, 50, 25, 12, 4)),
        Puzzle(625, listOf(125, 25, 10, 5, 4, 1)),
        Puzzle(360, listOf(90, 45, 12, 8, 5, 2)),
        Puzzle(144, listOf(12, 6, 3, 9, 4, 2)),
        Puzzle(196, listOf(14, 7, 2, 8, 5, 1)),
        Puzzle(225, listOf(15, 5, 3, 9, 10, 2)),
        Puzzle(324, listOf(18, 9, 6, 3, 12, 2)),
        Puzzle(441, listOf(21, 7, 3, 14, 6, 2)),
        Puzzle(576, listOf(24, 12, 6, 4, 3, 2)),
        Puzzle(729, listOf(27, 9, 3, 6, 12, 1)),
        Puzzle(1024, listOf(32, 16, 8, 4, 2, 1)),
        Puzzle(1331, listOf(11, 121, 10, 3, 7, 1)),
        Puzzle(1728, listOf(12, 144, 6, 4, 3, 2)),

        Puzzle(187, listOf(25, 7, 3, 4, 2, 1)),
        Puzzle(293, listOf(50, 6, 7, 3, 2, 1)),
        Puzzle(418, listOf(75, 5, 8, 6, 4, 2)),
        Puzzle(527, listOf(100, 5, 25, 2, 7, 3)),
        Puzzle(639, listOf(75, 8, 5, 6, 3, 1)),
        Puzzle(846, listOf(100, 8, 25, 7, 3, 2)),
        Puzzle(975, listOf(125, 8, 25, 5, 4, 1)),
        Puzzle(1184, listOf(150, 8, 12, 5, 3, 2)),
        Puzzle(1425, listOf(75, 19, 25, 5, 3, 1)),
        Puzzle(1680, listOf(120, 14, 7, 5, 4, 2)),

        Puzzle(312, listOf(50, 6, 2, 12, 8, 4)),
        Puzzle(426, listOf(75, 5, 9, 6, 2, 1)),
        Puzzle(538, listOf(100, 5, 7, 8, 4, 2)),
        Puzzle(647, listOf(90, 7, 6, 5, 3, 2)),
        Puzzle(759, listOf(100, 7, 8, 9, 6, 1)),
        Puzzle(864, listOf(96, 9, 6, 4, 3, 2)),
        Puzzle(936, listOf(100, 9, 25, 6, 4, 2)),
        Puzzle(1056, listOf(88, 12, 8, 6, 4, 2)),
        Puzzle(1296, listOf(36, 18, 9, 6, 4, 2)),
        Puzzle(1456, listOf(100, 14, 8, 7, 4, 2)),

        Puzzle(275, listOf(25, 11, 5, 4, 2, 1)),
        Puzzle(385, listOf(35, 11, 7, 5, 3, 1)),
        Puzzle(495, listOf(45, 11, 9, 5, 3, 1)),
        Puzzle(605, listOf(55, 11, 10, 5, 2, 1)),
        Puzzle(715, listOf(65, 11, 13, 5, 2, 1)),
        Puzzle(825, listOf(75, 11, 15, 5, 3, 1)),
        Puzzle(935, listOf(85, 11, 17, 5, 2, 1)),
        Puzzle(1045, listOf(95, 11, 19, 5, 4, 1)),
        Puzzle(1155, listOf(105, 11, 21, 5, 3, 1)),
        Puzzle(1265, listOf(115, 11, 23, 5, 2, 1)),

        Puzzle(243, listOf(81, 3, 9, 6, 2, 1)),
        Puzzle(486, listOf(81, 6, 9, 3, 2, 1)),
        Puzzle(567, listOf(63, 9, 7, 4, 3, 1)),
        Puzzle(648, listOf(72, 9, 8, 6, 3, 1)),
        Puzzle(729, listOf(81, 9, 9, 6, 3, 1)),
        Puzzle(810, listOf(90, 9, 10, 5, 3, 1)),
        Puzzle(891, listOf(99, 9, 11, 6, 3, 1)),
        Puzzle(972, listOf(108, 9, 12, 4, 3, 1)),
        Puzzle(1053, listOf(117, 9, 13, 5, 3, 1)),
        Puzzle(1134, listOf(126, 9, 14, 6, 3, 1)),

        Puzzle(333, listOf(100, 3, 33, 11, 7, 1)),
        Puzzle(444, listOf(100, 4, 44, 11, 8, 1)),
        Puzzle(555, listOf(100, 5, 55, 11, 9, 1)),
        Puzzle(666, listOf(100, 6, 66, 11, 10, 1)),
        Puzzle(777, listOf(100, 7, 77, 11, 10, 1)),
        Puzzle(888, listOf(100, 8, 88, 11, 10, 1)),
        Puzzle(999, listOf(100, 9, 99, 11, 10, 1)),
        Puzzle(1111, listOf(101, 11, 10, 9, 8, 1)),
        Puzzle(1221, listOf(111, 11, 10, 5, 3, 1)),
        Puzzle(1332, listOf(111, 12, 11, 6, 3, 1)),

        Puzzle(728, listOf(91, 8, 13, 7, 4, 1)),
        Puzzle(812, listOf(116, 7, 14, 6, 3, 1)),
        Puzzle(928, listOf(116, 8, 29, 4, 2, 1)),
        Puzzle(1044, listOf(87, 12, 29, 6, 3, 1)),
        Puzzle(1176, listOf(98, 12, 14, 7, 3, 1)),
        Puzzle(1288, listOf(92, 14, 23, 7, 4, 1)),
        Puzzle(1395, listOf(93, 15, 31, 6, 3, 1)),
        Puzzle(1512, listOf(108, 14, 27, 7, 2, 1)),
        Puzzle(1632, listOf(96, 17, 32, 8, 4, 1)),
        Puzzle(1764, listOf(126, 14, 21, 7, 3, 1)),

        Puzzle(212, listOf(50, 4, 3, 12, 8, 2)),
        Puzzle(318, listOf(75, 4, 6, 9, 3, 1)),
        Puzzle(424, listOf(100, 4, 6, 12, 8, 2)),
        Puzzle(530, listOf(100, 5, 6, 10, 3, 1)),
        Puzzle(636, listOf(100, 6, 6, 9, 4, 2)),
        Puzzle(742, listOf(100, 7, 6, 8, 5, 2)),
        Puzzle(848, listOf(100, 8, 6, 7, 4, 2)),
        Puzzle(954, listOf(100, 9, 6, 6, 3, 2)),
        Puzzle(1060, listOf(100, 10, 6, 5, 4, 2)),
        Puzzle(1166, listOf(100, 11, 6, 6, 5, 1)),

        Puzzle(351, listOf(39, 9, 13, 6, 3, 1)),
        Puzzle(459, listOf(51, 9, 17, 6, 3, 1)),
        Puzzle(567, listOf(63, 9, 21, 6, 3, 1)),
        Puzzle(675, listOf(75, 9, 25, 6, 3, 1)),
        Puzzle(783, listOf(87, 9, 29, 6, 3, 1)),
        Puzzle(891, listOf(99, 9, 33, 6, 3, 1)),
        Puzzle(999, listOf(111, 9, 37, 6, 3, 1)),
        Puzzle(1107, listOf(123, 9, 41, 6, 3, 1)),
        Puzzle(1215, listOf(135, 9, 45, 6, 3, 1)),
        Puzzle(1323, listOf(147, 9, 49, 6, 3, 1)),

        Puzzle(406, listOf(58, 7, 29, 5, 3, 1)),
        Puzzle(512, listOf(64, 8, 32, 6, 4, 1)),
        Puzzle(614, listOf(102, 6, 17, 5, 3, 1)),
        Puzzle(721, listOf(103, 7, 31, 4, 2, 1)),
        Puzzle(834, listOf(139, 6, 23, 5, 3, 1)),
        Puzzle(942, listOf(157, 6, 31, 4, 2, 1)),
        Puzzle(1152, listOf(144, 8, 36, 6, 4, 1)),
        Puzzle(1344, listOf(168, 8, 42, 6, 3, 1)),
        Puzzle(1536, listOf(192, 8, 48, 6, 4, 1)),
        Puzzle(1728, listOf(216, 8, 54, 6, 3, 1)),

        Puzzle(777, listOf(111, 7, 37, 3, 2, 1)),
        Puzzle(888, listOf(111, 8, 37, 3, 2, 1)),
        Puzzle(999, listOf(111, 9, 37, 3, 2, 1)),
        Puzzle(1110, listOf(111, 10, 37, 3, 2, 1)),
        Puzzle(1221, listOf(111, 11, 37, 3, 2, 1)),
        Puzzle(1332, listOf(111, 12, 37, 3, 2, 1)),
        Puzzle(1443, listOf(111, 13, 37, 3, 2, 1)),
        Puzzle(1554, listOf(111, 14, 37, 3, 2, 1)),
        Puzzle(1665, listOf(111, 15, 37, 3, 2, 1)),
        Puzzle(1776, listOf(111, 16, 37, 3, 2, 1)),
        Puzzle(238, listOf(34, 7, 17, 5, 3, 1)),
        Puzzle(276, listOf(46, 6, 23, 4, 2, 1)),
        Puzzle(315, listOf(45, 7, 15, 6, 3, 1)),
        Puzzle(378, listOf(54, 7, 18, 6, 3, 1)),
        Puzzle(462, listOf(66, 7, 22, 6, 3, 1)),
        Puzzle(525, listOf(75, 7, 25, 6, 3, 1)),
        Puzzle(588, listOf(84, 7, 28, 6, 3, 1)),
        Puzzle(651, listOf(93, 7, 31, 6, 3, 1)),
        Puzzle(714, listOf(102, 7, 34, 6, 3, 1)),
        Puzzle(777, listOf(111, 7, 37, 6, 3, 1)),

        Puzzle(248, listOf(31, 8, 16, 7, 4, 1)),
        Puzzle(296, listOf(37, 8, 19, 6, 4, 1)),
        Puzzle(344, listOf(43, 8, 22, 5, 4, 1)),
        Puzzle(392, listOf(49, 8, 25, 7, 4, 1)),
        Puzzle(448, listOf(56, 8, 28, 6, 4, 1)),
        Puzzle(496, listOf(62, 8, 31, 5, 4, 1)),
        Puzzle(552, listOf(69, 8, 23, 6, 4, 1)),
        Puzzle(608, listOf(76, 8, 38, 5, 4, 1)),
        Puzzle(664, listOf(83, 8, 41, 6, 4, 1)),
        Puzzle(720, listOf(90, 8, 45, 5, 4, 1)),

        Puzzle(342, listOf(38, 9, 19, 5, 3, 1)),
        Puzzle(423, listOf(47, 9, 23, 6, 3, 1)),
        Puzzle(504, listOf(56, 9, 28, 5, 3, 1)),
        Puzzle(585, listOf(65, 9, 13, 7, 3, 1)),
        Puzzle(666, listOf(74, 9, 37, 5, 3, 1)),
        Puzzle(747, listOf(83, 9, 41, 6, 3, 1)),
        Puzzle(828, listOf(92, 9, 46, 5, 3, 1)),
        Puzzle(909, listOf(101, 9, 33, 7, 3, 1)),
        Puzzle(990, listOf(110, 9, 55, 5, 3, 1)),
        Puzzle(1071, listOf(119, 9, 17, 7, 3, 1)),

        Puzzle(420, listOf(42, 10, 21, 6, 5, 1)),
        Puzzle(560, listOf(56, 10, 28, 7, 5, 1)),
        Puzzle(690, listOf(69, 10, 23, 8, 5, 1)),
        Puzzle(840, listOf(84, 10, 42, 6, 5, 1)),
        Puzzle(970, listOf(97, 10, 19, 8, 5, 1)),
        Puzzle(1120, listOf(112, 10, 28, 7, 5, 1)),
        Puzzle(1260, listOf(126, 10, 42, 6, 5, 1)),
        Puzzle(1390, listOf(139, 10, 31, 8, 5, 1)),
        Puzzle(1540, listOf(154, 10, 22, 7, 5, 1)),
        Puzzle(1680, listOf(168, 10, 42, 6, 5, 1)),

        Puzzle(299, listOf(23, 13, 11, 7, 3, 1)),
        Puzzle(377, listOf(29, 13, 17, 6, 3, 1)),
        Puzzle(481, listOf(37, 13, 19, 5, 3, 1)),
        Puzzle(559, listOf(43, 13, 31, 4, 2, 1)),
        Puzzle(637, listOf(49, 13, 23, 6, 3, 1)),
        Puzzle(767, listOf(59, 13, 29, 5, 3, 1)),
        Puzzle(871, listOf(67, 13, 31, 6, 3, 1)),
        Puzzle(949, listOf(73, 13, 37, 5, 3, 1)),
        Puzzle(1079, listOf(83, 13, 41, 6, 3, 1)),
        Puzzle(1183, listOf(91, 13, 39, 4, 2, 1)),

        Puzzle(272, listOf(16, 17, 8, 4, 2, 1)),
        Puzzle(374, listOf(22, 17, 11, 5, 3, 1)),
        Puzzle(476, listOf(28, 17, 14, 6, 3, 1)),
        Puzzle(578, listOf(34, 17, 19, 5, 3, 1)),
        Puzzle(680, listOf(40, 17, 20, 6, 4, 1)),
        Puzzle(782, listOf(46, 17, 23, 5, 3, 1)),
        Puzzle(884, listOf(52, 17, 26, 6, 3, 1)),
        Puzzle(986, listOf(58, 17, 29, 5, 3, 1)),
        Puzzle(1088, listOf(64, 17, 32, 6, 4, 1)),
        Puzzle(1190, listOf(70, 17, 35, 6, 5, 1)),

        Puzzle(361, listOf(19, 19, 10, 7, 3, 1)),
        Puzzle(494, listOf(26, 19, 13, 6, 3, 1)),
        Puzzle(627, listOf(33, 19, 11, 7, 3, 1)),
        Puzzle(760, listOf(40, 19, 20, 6, 4, 1)),
        Puzzle(893, listOf(47, 19, 23, 5, 3, 1)),
        Puzzle(1026, listOf(54, 19, 27, 6, 3, 1)),
        Puzzle(1159, listOf(61, 19, 31, 5, 3, 1)),
        Puzzle(1292, listOf(68, 19, 34, 6, 4, 1)),
        Puzzle(1425, listOf(75, 19, 25, 6, 5, 1)),
        Puzzle(1558, listOf(82, 19, 41, 5, 3, 1)),

        Puzzle(484, listOf(22, 22, 11, 8, 4, 1)),
        Puzzle(572, listOf(26, 22, 13, 6, 4, 1)),
        Puzzle(704, listOf(32, 22, 16, 7, 4, 1)),
        Puzzle(836, listOf(38, 22, 19, 6, 4, 1)),
        Puzzle(968, listOf(44, 22, 11, 8, 4, 1)),
        Puzzle(1100, listOf(50, 22, 25, 6, 4, 1)),
        Puzzle(1232, listOf(56, 22, 28, 7, 4, 1)),
        Puzzle(1364, listOf(62, 22, 31, 6, 4, 1)),
        Puzzle(1496, listOf(68, 22, 34, 7, 4, 1)),
        Puzzle(1628, listOf(74, 22, 37, 6, 4, 1)),

        Puzzle(529, listOf(23, 23, 11, 7, 5, 1)),
        Puzzle(667, listOf(29, 23, 17, 6, 5, 1)),
        Puzzle(805, listOf(35, 23, 7, 9, 5, 1)),
        Puzzle(943, listOf(41, 23, 19, 6, 5, 1)),
        Puzzle(1081, listOf(47, 23, 13, 8, 5, 1)),
        Puzzle(1219, listOf(53, 23, 31, 5, 3, 1)),
        Puzzle(1357, listOf(59, 23, 17, 7, 5, 1)),
        Puzzle(1495, listOf(65, 23, 13, 9, 5, 1)),
        Puzzle(1633, listOf(71, 23, 29, 6, 5, 1)),
        Puzzle(1771, listOf(77, 23, 11, 8, 5, 1)),

        Puzzle(625, listOf(25, 25, 10, 5, 4, 1)),
        Puzzle(750, listOf(30, 25, 15, 6, 5, 1)),
        Puzzle(875, listOf(35, 25, 7, 8, 5, 1)),
        Puzzle(1000, listOf(40, 25, 20, 5, 4, 1)),
        Puzzle(1125, listOf(45, 25, 15, 6, 5, 1)),
        Puzzle(1250, listOf(50, 25, 10, 7, 5, 1)),
        Puzzle(1375, listOf(55, 25, 11, 8, 5, 1)),
        Puzzle(1500, listOf(60, 25, 15, 6, 4, 1)),
        Puzzle(1625, listOf(65, 25, 13, 7, 5, 1)),
        Puzzle(1750, listOf(70, 25, 14, 8, 5, 1)),

        Puzzle(676, listOf(26, 26, 13, 8, 4, 1)),
        Puzzle(806, listOf(31, 26, 17, 6, 4, 1)),
        Puzzle(936, listOf(36, 26, 18, 7, 4, 1)),
        Puzzle(1066, listOf(41, 26, 19, 8, 4, 1)),
        Puzzle(1196, listOf(46, 26, 23, 6, 4, 1)),
        Puzzle(1326, listOf(51, 26, 17, 7, 4, 1)),
        Puzzle(1456, listOf(56, 26, 28, 8, 4, 1)),
        Puzzle(1586, listOf(61, 26, 13, 9, 4, 1)),
        Puzzle(1716, listOf(66, 26, 22, 7, 4, 1)),
        Puzzle(1846, listOf(71, 26, 31, 6, 4, 1)),

        Puzzle(841, listOf(29, 29, 14, 7, 5, 1)),
        Puzzle(986, listOf(34, 29, 17, 6, 5, 1)),
        Puzzle(1131, listOf(39, 29, 13, 8, 5, 1)),
        Puzzle(1276, listOf(44, 29, 22, 7, 5, 1)),
        Puzzle(1421, listOf(49, 29, 7, 9, 5, 1)),
        Puzzle(1566, listOf(54, 29, 18, 8, 5, 1)),
        Puzzle(1711, listOf(59, 29, 31, 6, 5, 1)),
        Puzzle(1856, listOf(64, 29, 16, 7, 5, 1)),
        Puzzle(2001, listOf(69, 29, 23, 8, 5, 1)),
        Puzzle(2146, listOf(74, 29, 37, 6, 5, 1)),

        Puzzle(961, listOf(31, 31, 15, 7, 5, 1)),
        Puzzle(1116, listOf(36, 31, 18, 6, 5, 1)),
        Puzzle(1271, listOf(41, 31, 13, 8, 5, 1)),
        Puzzle(1426, listOf(46, 31, 23, 7, 5, 1)),
        Puzzle(1581, listOf(51, 31, 17, 6, 5, 1)),
        Puzzle(1736, listOf(56, 31, 28, 8, 5, 1)),
        Puzzle(1891, listOf(61, 31, 19, 7, 5, 1)),
        Puzzle(2046, listOf(66, 31, 22, 6, 5, 1)),
        Puzzle(2201, listOf(71, 31, 29, 8, 5, 1)),
        Puzzle(2356, listOf(76, 31, 38, 7, 5, 1)),
        Puzzle(289, listOf(17, 17, 8, 6, 3, 1)),
        Puzzle(391, listOf(23, 17, 11, 7, 3, 1)),
        Puzzle(493, listOf(29, 17, 13, 6, 3, 1)),
        Puzzle(595, listOf(35, 17, 7, 8, 5, 1)),
        Puzzle(697, listOf(41, 17, 19, 6, 3, 1)),
        Puzzle(799, listOf(47, 17, 23, 5, 3, 1)),
        Puzzle(901, listOf(53, 17, 13, 7, 4, 1)),
        Puzzle(1003, listOf(59, 17, 31, 6, 3, 1)),
        Puzzle(1105, listOf(65, 17, 13, 8, 5, 1)),
        Puzzle(1207, listOf(71, 17, 29, 6, 3, 1)),

        Puzzle(343, listOf(49, 7, 14, 6, 3, 1)),
        Puzzle(512, listOf(64, 8, 16, 4, 2, 1)),
        Puzzle(729, listOf(81, 9, 27, 3, 2, 1)),
        Puzzle(1000, listOf(100, 10, 25, 4, 2, 1)),
        Puzzle(1331, listOf(121, 11, 22, 6, 3, 1)),
        Puzzle(1728, listOf(144, 12, 36, 4, 3, 1)),
        Puzzle(2197, listOf(169, 13, 26, 6, 3, 1)),
        Puzzle(2744, listOf(196, 14, 28, 7, 4, 1)),
        Puzzle(3375, listOf(225, 15, 45, 5, 3, 1)),
        Puzzle(4096, listOf(256, 16, 64, 4, 2, 1)),

        Puzzle(487, listOf(50, 9, 37, 8, 4, 1)),
        Puzzle(594, listOf(75, 7, 23, 9, 5, 1)),
        Puzzle(703, listOf(100, 7, 29, 6, 3, 1)),
        Puzzle(817, listOf(125, 6, 31, 5, 4, 1)),
        Puzzle(926, listOf(150, 6, 13, 8, 3, 1)),
        Puzzle(1039, listOf(175, 5, 41, 7, 4, 1)),
        Puzzle(1148, listOf(200, 5, 37, 6, 3, 1)),
        Puzzle(1261, listOf(225, 5, 17, 8, 4, 1)),
        Puzzle(1374, listOf(250, 5, 31, 7, 3, 1)),
        Puzzle(1487, listOf(275, 5, 37, 6, 4, 1)),

        Puzzle(288, listOf(12, 24, 6, 8, 4, 2)),
        Puzzle(432, listOf(18, 24, 9, 6, 4, 2)),
        Puzzle(576, listOf(24, 24, 12, 6, 4, 2)),
        Puzzle(720, listOf(30, 24, 15, 6, 4, 2)),
        Puzzle(864, listOf(36, 24, 18, 6, 4, 2)),
        Puzzle(1008, listOf(42, 24, 21, 6, 4, 2)),
        Puzzle(1152, listOf(48, 24, 24, 6, 4, 2)),
        Puzzle(1296, listOf(54, 24, 27, 6, 4, 2)),
        Puzzle(1440, listOf(60, 24, 30, 6, 4, 2)),
        Puzzle(1584, listOf(66, 24, 33, 6, 4, 2)),

        Puzzle(336, listOf(14, 24, 7, 8, 4, 2)),
        Puzzle(504, listOf(21, 24, 7, 9, 4, 2)),
        Puzzle(672, listOf(28, 24, 14, 6, 4, 2)),
        Puzzle(840, listOf(35, 24, 7, 10, 4, 2)),
        Puzzle(1008, listOf(42, 24, 14, 8, 4, 2)),
        Puzzle(1176, listOf(49, 24, 7, 12, 4, 2)),
        Puzzle(1344, listOf(56, 24, 14, 6, 8, 2)),
        Puzzle(1512, listOf(63, 24, 21, 6, 4, 2)),
        Puzzle(1680, listOf(70, 24, 14, 10, 4, 2)),
        Puzzle(1848, listOf(77, 24, 11, 8, 4, 2)),

        Puzzle(455, listOf(65, 7, 13, 5, 2, 1)),
        Puzzle(546, listOf(78, 7, 13, 6, 3, 1)),
        Puzzle(637, listOf(91, 7, 13, 7, 4, 1)),
        Puzzle(728, listOf(104, 7, 13, 8, 4, 1)),
        Puzzle(819, listOf(117, 7, 13, 9, 3, 1)),
        Puzzle(910, listOf(130, 7, 13, 10, 5, 1)),
        Puzzle(1001, listOf(143, 7, 13, 11, 4, 1)),
        Puzzle(1092, listOf(156, 7, 13, 12, 3, 1)),
        Puzzle(1183, listOf(169, 7, 13, 13, 2, 1)),
        Puzzle(1274, listOf(182, 7, 13, 14, 4, 1)),

        Puzzle(299, listOf(13, 23, 7, 5, 3, 1)),
        Puzzle(391, listOf(17, 23, 11, 6, 3, 1)),
        Puzzle(483, listOf(21, 23, 7, 8, 3, 1)),
        Puzzle(575, listOf(25, 23, 5, 10, 4, 1)),
        Puzzle(667, listOf(29, 23, 17, 6, 4, 1)),
        Puzzle(759, listOf(33, 23, 11, 8, 3, 1)),
        Puzzle(851, listOf(37, 23, 19, 6, 3, 1)),
        Puzzle(943, listOf(41, 23, 13, 9, 3, 1)),
        Puzzle(1035, listOf(45, 23, 15, 10, 5, 1)),
        Puzzle(1127, listOf(49, 23, 7, 12, 4, 1)),

        Puzzle(518, listOf(37, 14, 7, 8, 4, 1)),
        Puzzle(644, listOf(46, 14, 23, 5, 3, 1)),
        Puzzle(770, listOf(55, 14, 11, 7, 5, 1)),
        Puzzle(896, listOf(64, 14, 32, 6, 4, 1)),
        Puzzle(1022, listOf(73, 14, 19, 8, 3, 1)),
        Puzzle(1148, listOf(82, 14, 41, 5, 3, 1)),
        Puzzle(1274, listOf(91, 14, 13, 9, 4, 1)),
        Puzzle(1400, listOf(100, 14, 25, 7, 4, 1)),
        Puzzle(1526, listOf(109, 14, 31, 6, 3, 1)),
        Puzzle(1652, listOf(118, 14, 59, 4, 2, 1)),

        Puzzle(441, listOf(49, 9, 7, 6, 3, 1)),
        Puzzle(567, listOf(63, 9, 21, 6, 3, 1)),
        Puzzle(693, listOf(77, 9, 11, 8, 4, 1)),
        Puzzle(819, listOf(91, 9, 13, 7, 3, 1)),
        Puzzle(945, listOf(105, 9, 15, 8, 5, 1)),
        Puzzle(1071, listOf(119, 9, 17, 7, 3, 1)),
        Puzzle(1197, listOf(133, 9, 19, 8, 4, 1)),
        Puzzle(1323, listOf(147, 9, 21, 7, 3, 1)),
        Puzzle(1449, listOf(161, 9, 23, 8, 4, 1)),
        Puzzle(1575, listOf(175, 9, 25, 7, 5, 1)),

        Puzzle(371, listOf(53, 7, 11, 8, 4, 1)),
        Puzzle(477, listOf(53, 9, 17, 6, 3, 1)),
        Puzzle(583, listOf(53, 11, 29, 4, 2, 1)),
        Puzzle(689, listOf(53, 13, 17, 8, 4, 1)),
        Puzzle(795, listOf(53, 15, 31, 6, 3, 1)),
        Puzzle(901, listOf(53, 17, 11, 9, 3, 1)),
        Puzzle(1007, listOf(53, 19, 29, 7, 4, 1)),
        Puzzle(1113, listOf(53, 21, 37, 6, 3, 1)),
        Puzzle(1219, listOf(53, 23, 17, 8, 5, 1)),
        Puzzle(1325, listOf(53, 25, 31, 7, 5, 1)),

        Puzzle(468, listOf(52, 9, 13, 6, 3, 1)),
        Puzzle(572, listOf(52, 11, 26, 6, 4, 1)),
        Puzzle(676, listOf(52, 13, 26, 7, 4, 1)),
        Puzzle(780, listOf(52, 15, 20, 8, 5, 1)),
        Puzzle(884, listOf(52, 17, 34, 6, 3, 1)),
        Puzzle(988, listOf(52, 19, 13, 8, 4, 1)),
        Puzzle(1092, listOf(52, 21, 26, 7, 4, 1)),
        Puzzle(1196, listOf(52, 23, 46, 6, 3, 1)),
        Puzzle(1300, listOf(52, 25, 13, 8, 5, 1)),
        Puzzle(1404, listOf(52, 27, 39, 6, 3, 1)),
        Puzzle(322, listOf(46, 7, 23, 5, 3, 1)),
        Puzzle(391, listOf(23, 17, 13, 6, 4, 1)),
        Puzzle(506, listOf(46, 11, 23, 7, 2, 1)),
        Puzzle(621, listOf(69, 9, 23, 6, 3, 1)),
        Puzzle(736, listOf(92, 8, 23, 7, 4, 1)),
        Puzzle(851, listOf(37, 23, 19, 6, 3, 1)),
        Puzzle(966, listOf(42, 23, 21, 7, 3, 1)),
        Puzzle(1081, listOf(47, 23, 13, 8, 5, 1)),
        Puzzle(1196, listOf(52, 23, 26, 7, 4, 1)),
        Puzzle(1311, listOf(57, 23, 19, 6, 3, 1)),

        Puzzle(343, listOf(49, 7, 21, 5, 3, 1)),
        Puzzle(434, listOf(62, 7, 31, 4, 2, 1)),
        Puzzle(525, listOf(75, 7, 25, 6, 3, 1)),
        Puzzle(616, listOf(88, 7, 22, 6, 4, 1)),
        Puzzle(707, listOf(101, 7, 29, 5, 3, 1)),
        Puzzle(798, listOf(114, 7, 19, 6, 4, 1)),
        Puzzle(889, listOf(127, 7, 31, 5, 3, 1)),
        Puzzle(980, listOf(140, 7, 35, 6, 4, 1)),
        Puzzle(1071, listOf(153, 7, 17, 8, 3, 1)),
        Puzzle(1162, listOf(166, 7, 83, 4, 2, 1)),

        Puzzle(356, listOf(89, 4, 17, 6, 3, 1)),
        Puzzle(445, listOf(89, 5, 17, 6, 3, 1)),
        Puzzle(534, listOf(89, 6, 17, 6, 3, 1)),
        Puzzle(623, listOf(89, 7, 17, 6, 3, 1)),
        Puzzle(712, listOf(89, 8, 17, 6, 3, 1)),
        Puzzle(801, listOf(89, 9, 17, 6, 3, 1)),
        Puzzle(890, listOf(89, 10, 17, 6, 3, 1)),
        Puzzle(979, listOf(89, 11, 17, 6, 3, 1)),
        Puzzle(1068, listOf(89, 12, 17, 6, 3, 1)),
        Puzzle(1157, listOf(89, 13, 17, 6, 3, 1)),

        Puzzle(412, listOf(103, 4, 29, 5, 3, 1)),
        Puzzle(515, listOf(103, 5, 29, 6, 3, 1)),
        Puzzle(618, listOf(103, 6, 29, 7, 3, 1)),
        Puzzle(721, listOf(103, 7, 29, 8, 4, 1)),
        Puzzle(824, listOf(103, 8, 29, 5, 4, 1)),
        Puzzle(927, listOf(103, 9, 29, 6, 3, 1)),
        Puzzle(1030, listOf(103, 10, 29, 7, 5, 1)),
        Puzzle(1133, listOf(103, 11, 29, 8, 4, 1)),
        Puzzle(1236, listOf(103, 12, 29, 6, 3, 1)),
        Puzzle(1339, listOf(103, 13, 29, 7, 4, 1)),

        Puzzle(444, listOf(111, 4, 37, 3, 2, 1)),
        Puzzle(555, listOf(111, 5, 37, 3, 2, 1)),
        Puzzle(666, listOf(111, 6, 37, 3, 2, 1)),
        Puzzle(777, listOf(111, 7, 37, 3, 2, 1)),
        Puzzle(888, listOf(111, 8, 37, 3, 2, 1)),
        Puzzle(999, listOf(111, 9, 37, 3, 2, 1)),
        Puzzle(1110, listOf(111, 10, 37, 3, 2, 1)),
        Puzzle(1221, listOf(111, 11, 37, 3, 2, 1)),
        Puzzle(1332, listOf(111, 12, 37, 3, 2, 1)),
        Puzzle(1443, listOf(111, 13, 37, 3, 2, 1)),

        Puzzle(384, listOf(48, 8, 16, 6, 4, 1)),
        Puzzle(496, listOf(62, 8, 31, 5, 4, 1)),
        Puzzle(608, listOf(76, 8, 19, 7, 4, 1)),
        Puzzle(720, listOf(90, 8, 45, 5, 4, 1)),
        Puzzle(832, listOf(104, 8, 26, 6, 4, 1)),
        Puzzle(944, listOf(118, 8, 59, 4, 2, 1)),
        Puzzle(1056, listOf(132, 8, 33, 7, 4, 1)),
        Puzzle(1168, listOf(146, 8, 73, 4, 2, 1)),
        Puzzle(1280, listOf(160, 8, 32, 6, 5, 1)),
        Puzzle(1392, listOf(174, 8, 87, 4, 2, 1)),

        Puzzle(405, listOf(45, 9, 15, 6, 3, 1)),
        Puzzle(522, listOf(58, 9, 29, 5, 3, 1)),
        Puzzle(639, listOf(71, 9, 17, 7, 3, 1)),
        Puzzle(756, listOf(84, 9, 21, 6, 4, 1)),
        Puzzle(873, listOf(97, 9, 19, 8, 4, 1)),
        Puzzle(990, listOf(110, 9, 55, 5, 3, 1)),
        Puzzle(1107, listOf(123, 9, 41, 6, 3, 1)),
        Puzzle(1224, listOf(136, 9, 34, 7, 4, 1)),
        Puzzle(1341, listOf(149, 9, 23, 8, 3, 1)),
        Puzzle(1458, listOf(162, 9, 54, 6, 3, 1)),

        Puzzle(430, listOf(43, 10, 17, 8, 5, 1)),
        Puzzle(570, listOf(57, 10, 19, 6, 5, 1)),
        Puzzle(710, listOf(71, 10, 29, 7, 5, 1)),
        Puzzle(850, listOf(85, 10, 17, 8, 5, 1)),
        Puzzle(990, listOf(99, 10, 33, 6, 5, 1)),
        Puzzle(1130, listOf(113, 10, 31, 7, 5, 1)),
        Puzzle(1270, listOf(127, 10, 19, 8, 5, 1)),
        Puzzle(1410, listOf(141, 10, 47, 6, 5, 1)),
        Puzzle(1550, listOf(155, 10, 31, 7, 5, 1)),
        Puzzle(1690, listOf(169, 10, 13, 8, 5, 1)),

        Puzzle(484, listOf(44, 11, 22, 6, 3, 1)),
        Puzzle(605, listOf(55, 11, 25, 5, 3, 1)),
        Puzzle(726, listOf(66, 11, 33, 6, 3, 1)),
        Puzzle(847, listOf(77, 11, 7, 9, 3, 1)),
        Puzzle(968, listOf(88, 11, 44, 6, 4, 1)),
        Puzzle(1089, listOf(99, 11, 33, 7, 3, 1)),
        Puzzle(1210, listOf(110, 11, 55, 5, 3, 1)),
        Puzzle(1331, listOf(121, 11, 22, 6, 3, 1)),
        Puzzle(1452, listOf(132, 11, 44, 7, 4, 1)),
        Puzzle(1573, listOf(143, 11, 13, 8, 3, 1)),

        Puzzle(516, listOf(43, 12, 29, 5, 3, 1)),
        Puzzle(648, listOf(54, 12, 18, 6, 3, 1)),
        Puzzle(780, listOf(65, 12, 13, 8, 5, 1)),
        Puzzle(912, listOf(76, 12, 19, 7, 4, 1)),
        Puzzle(1044, listOf(87, 12, 29, 6, 3, 1)),
        Puzzle(1176, listOf(98, 12, 14, 7, 3, 1)),
        Puzzle(1308, listOf(109, 12, 31, 8, 4, 1)),
        Puzzle(1440, listOf(120, 12, 24, 6, 5, 1)),
        Puzzle(1572, listOf(131, 12, 29, 7, 3, 1)),
        Puzzle(1704, listOf(142, 12, 71, 4, 2, 1)),

        Puzzle(585, listOf(45, 13, 15, 7, 3, 1)),
        Puzzle(715, listOf(55, 13, 11, 8, 5, 1)),
        Puzzle(845, listOf(65, 13, 13, 9, 5, 1)),
        Puzzle(975, listOf(75, 13, 25, 6, 3, 1)),
        Puzzle(1105, listOf(85, 13, 17, 8, 5, 1)),
        Puzzle(1235, listOf(95, 13, 19, 7, 5, 1)),
        Puzzle(1365, listOf(105, 13, 21, 6, 5, 1)),
        Puzzle(1495, listOf(115, 13, 23, 8, 5, 1)),
        Puzzle(1625, listOf(125, 13, 25, 7, 5, 1)),
        Puzzle(1755, listOf(135, 13, 27, 6, 5, 1)),

        Puzzle(616, listOf(44, 14, 22, 7, 4, 1)),
        Puzzle(756, listOf(54, 14, 27, 6, 4, 1)),
        Puzzle(896, listOf(64, 14, 32, 6, 4, 1)),
        Puzzle(1036, listOf(74, 14, 37, 7, 4, 1)),
        Puzzle(1176, listOf(84, 14, 21, 8, 4, 1)),
        Puzzle(1316, listOf(94, 14, 47, 6, 4, 1)),
        Puzzle(1456, listOf(104, 14, 26, 7, 4, 1)),
        Puzzle(1596, listOf(114, 14, 19, 8, 4, 1)),
        Puzzle(1736, listOf(124, 14, 31, 6, 4, 1)),
        Puzzle(1876, listOf(134, 14, 67, 4, 2, 1)),

        Puzzle(675, listOf(45, 15, 25, 6, 5, 1)),
        Puzzle(825, listOf(55, 15, 11, 8, 5, 1)),
        Puzzle(975, listOf(65, 15, 13, 7, 5, 1)),
        Puzzle(1125, listOf(75, 15, 25, 6, 5, 1)),
        Puzzle(1275, listOf(85, 15, 17, 8, 5, 1)),
        Puzzle(1425, listOf(95, 15, 19, 7, 5, 1)),
        Puzzle(1575, listOf(105, 15, 21, 6, 5, 1)),
        Puzzle(1725, listOf(115, 15, 23, 8, 5, 1)),
        Puzzle(1875, listOf(125, 15, 25, 7, 5, 1)),
        Puzzle(2025, listOf(135, 15, 27, 6, 5, 1)),
        Puzzle(704, listOf(44, 16, 22, 8, 4, 1)),
        Puzzle(832, listOf(52, 16, 26, 6, 4, 1)),
        Puzzle(960, listOf(60, 16, 20, 8, 5, 1)),
        Puzzle(1088, listOf(68, 16, 34, 7, 4, 1)),
        Puzzle(1216, listOf(76, 16, 19, 8, 4, 1)),
        Puzzle(1344, listOf(84, 16, 28, 6, 4, 1)),
        Puzzle(1472, listOf(92, 16, 23, 8, 4, 1)),
        Puzzle(1600, listOf(100, 16, 25, 8, 4, 1)),
        Puzzle(1728, listOf(108, 16, 27, 8, 4, 1)),
        Puzzle(1856, listOf(116, 16, 29, 8, 4, 1)),

        Puzzle(765, listOf(45, 17, 15, 8, 5, 1)),
        Puzzle(901, listOf(53, 17, 13, 7, 4, 1)),
        Puzzle(1037, listOf(61, 17, 31, 6, 3, 1)),
        Puzzle(1173, listOf(69, 17, 23, 7, 3, 1)),
        Puzzle(1309, listOf(77, 17, 11, 8, 5, 1)),
        Puzzle(1445, listOf(85, 17, 25, 7, 5, 1)),
        Puzzle(1581, listOf(93, 17, 31, 6, 3, 1)),
        Puzzle(1717, listOf(101, 17, 29, 8, 4, 1)),
        Puzzle(1853, listOf(109, 17, 13, 7, 5, 1)),
        Puzzle(1989, listOf(117, 17, 39, 6, 3, 1)),

        Puzzle(810, listOf(45, 18, 15, 8, 5, 1)),
        Puzzle(972, listOf(54, 18, 27, 6, 3, 1)),
        Puzzle(1134, listOf(63, 18, 21, 7, 3, 1)),
        Puzzle(1296, listOf(72, 18, 36, 6, 4, 1)),
        Puzzle(1458, listOf(81, 18, 27, 8, 3, 1)),
        Puzzle(1620, listOf(90, 18, 30, 6, 5, 1)),
        Puzzle(1782, listOf(99, 18, 33, 7, 3, 1)),
        Puzzle(1944, listOf(108, 18, 27, 8, 4, 1)),
        Puzzle(2106, listOf(117, 18, 39, 6, 3, 1)),
        Puzzle(2268, listOf(126, 18, 42, 7, 3, 1)),

        Puzzle(855, listOf(45, 19, 15, 8, 5, 1)),
        Puzzle(1007, listOf(53, 19, 29, 7, 4, 1)),
        Puzzle(1159, listOf(61, 19, 31, 6, 3, 1)),
        Puzzle(1311, listOf(69, 19, 23, 7, 3, 1)),
        Puzzle(1463, listOf(77, 19, 11, 8, 5, 1)),
        Puzzle(1615, listOf(85, 19, 17, 6, 5, 1)),
        Puzzle(1767, listOf(93, 19, 31, 7, 3, 1)),
        Puzzle(1919, listOf(101, 19, 29, 8, 4, 1)),
        Puzzle(2071, listOf(109, 19, 13, 7, 5, 1)),
        Puzzle(2223, listOf(117, 19, 39, 6, 3, 1)),

        Puzzle(900, listOf(45, 20, 15, 6, 5, 1)),
        Puzzle(1060, listOf(53, 20, 10, 8, 5, 1)),
        Puzzle(1220, listOf(61, 20, 31, 6, 5, 1)),
        Puzzle(1380, listOf(69, 20, 23, 7, 5, 1)),
        Puzzle(1540, listOf(77, 20, 11, 8, 5, 1)),
        Puzzle(1700, listOf(85, 20, 17, 6, 5, 1)),
        Puzzle(1860, listOf(93, 20, 31, 7, 5, 1)),
        Puzzle(2020, listOf(101, 20, 29, 8, 5, 1)),
        Puzzle(2180, listOf(109, 20, 13, 7, 5, 1)),
        Puzzle(2340, listOf(117, 20, 39, 6, 5, 1)),

        Puzzle(945, listOf(45, 21, 15, 7, 3, 1)),
        Puzzle(1113, listOf(53, 21, 37, 6, 3, 1)),
        Puzzle(1281, listOf(61, 21, 31, 7, 3, 1)),
        Puzzle(1449, listOf(69, 21, 23, 8, 3, 1)),
        Puzzle(1617, listOf(77, 21, 11, 7, 3, 1)),
        Puzzle(1785, listOf(85, 21, 17, 6, 3, 1)),
        Puzzle(1953, listOf(93, 21, 31, 7, 3, 1)),
        Puzzle(2121, listOf(101, 21, 29, 8, 3, 1)),
        Puzzle(2289, listOf(109, 21, 13, 7, 3, 1)),
        Puzzle(2457, listOf(117, 21, 39, 6, 3, 1)),

        Puzzle(990, listOf(45, 22, 15, 8, 4, 1)),
        Puzzle(1166, listOf(53, 22, 11, 7, 4, 1)),
        Puzzle(1342, listOf(61, 22, 31, 6, 4, 1)),
        Puzzle(1518, listOf(69, 22, 23, 7, 4, 1)),
        Puzzle(1694, listOf(77, 22, 11, 8, 4, 1)),
        Puzzle(1870, listOf(85, 22, 17, 6, 4, 1)),
        Puzzle(2046, listOf(93, 22, 31, 7, 4, 1)),
        Puzzle(2222, listOf(101, 22, 29, 8, 4, 1)),
        Puzzle(2398, listOf(109, 22, 13, 7, 4, 1)),
        Puzzle(2574, listOf(117, 22, 39, 6, 4, 1)),

        Puzzle(1080, listOf(45, 24, 15, 8, 4, 1)),
        Puzzle(1272, listOf(53, 24, 17, 6, 4, 1)),
        Puzzle(1464, listOf(61, 24, 31, 7, 4, 1)),
        Puzzle(1656, listOf(69, 24, 23, 8, 4, 1)),
        Puzzle(1848, listOf(77, 24, 11, 7, 4, 1)),
        Puzzle(2040, listOf(85, 24, 17, 6, 4, 1)),
        Puzzle(2232, listOf(93, 24, 31, 7, 4, 1)),
        Puzzle(2424, listOf(101, 24, 29, 8, 4, 1)),
        Puzzle(2616, listOf(109, 24, 13, 7, 4, 1)),
        Puzzle(2808, listOf(117, 24, 39, 6, 4, 1)),

        Puzzle(1125, listOf(45, 25, 15, 6, 5, 1)),
        Puzzle(1325, listOf(53, 25, 10, 8, 5, 1)),
        Puzzle(1525, listOf(61, 25, 31, 6, 5, 1)),
        Puzzle(1725, listOf(69, 25, 23, 7, 5, 1)),
        Puzzle(1925, listOf(77, 25, 11, 8, 5, 1)),
        Puzzle(2125, listOf(85, 25, 17, 6, 5, 1)),
        Puzzle(2325, listOf(93, 25, 31, 7, 5, 1)),
        Puzzle(2525, listOf(101, 25, 29, 8, 5, 1)),
        Puzzle(2725, listOf(109, 25, 13, 7, 5, 1)),
        Puzzle(2925, listOf(117, 25, 39, 6, 5, 1)),

        Puzzle(1215, listOf(45, 27, 15, 8, 3, 1)),
        Puzzle(1431, listOf(53, 27, 17, 6, 3, 1)),
        Puzzle(1647, listOf(61, 27, 31, 7, 3, 1)),
        Puzzle(1863, listOf(69, 27, 23, 8, 3, 1)),
        Puzzle(2079, listOf(77, 27, 11, 7, 3, 1)),
        Puzzle(2295, listOf(85, 27, 17, 6, 3, 1)),
        Puzzle(2511, listOf(93, 27, 31, 7, 3, 1)),
        Puzzle(2727, listOf(101, 27, 29, 8, 3, 1)),
        Puzzle(2943, listOf(109, 27, 13, 7, 3, 1)),
        Puzzle(3159, listOf(117, 27, 39, 6, 3, 1)),

        Puzzle(1305, listOf(45, 29, 15, 8, 5, 1)),
        Puzzle(1537, listOf(53, 29, 17, 6, 5, 1)),
        Puzzle(1769, listOf(61, 29, 31, 7, 5, 1)),
        Puzzle(2001, listOf(69, 29, 23, 8, 5, 1)),
        Puzzle(2233, listOf(77, 29, 11, 7, 5, 1)),
        Puzzle(2465, listOf(85, 29, 17, 6, 5, 1)),
        Puzzle(2697, listOf(93, 29, 31, 7, 5, 1)),
        Puzzle(2929, listOf(101, 29, 29, 8, 5, 1)),
        Puzzle(3161, listOf(109, 29, 13, 7, 5, 1)),
        Puzzle(3393, listOf(117, 29, 39, 6, 5, 1)),

        Puzzle(1395, listOf(45, 31, 15, 8, 5, 1)),
        Puzzle(1643, listOf(53, 31, 17, 6, 5, 1)),
        Puzzle(1891, listOf(61, 31, 31, 7, 5, 1)),
        Puzzle(2139, listOf(69, 31, 23, 8, 5, 1)),
        Puzzle(2387, listOf(77, 31, 11, 7, 5, 1)),
        Puzzle(2635, listOf(85, 31, 17, 6, 5, 1)),
        Puzzle(2883, listOf(93, 31, 31, 7, 5, 1)),
        Puzzle(3131, listOf(101, 31, 29, 8, 5, 1)),
        Puzzle(3379, listOf(109, 31, 13, 7, 5, 1)),
        Puzzle(3627, listOf(117, 31, 39, 6, 5, 1)),

        Puzzle(1505, listOf(43, 35, 15, 8, 5, 1)),
        Puzzle(1785, listOf(51, 35, 17, 6, 5, 1)),
        Puzzle(2065, listOf(59, 35, 31, 7, 5, 1)),
        Puzzle(2345, listOf(67, 35, 23, 8, 5, 1)),
        Puzzle(2625, listOf(75, 35, 25, 7, 5, 1)),
        Puzzle(2905, listOf(83, 35, 17, 6, 5, 1)),
        Puzzle(3185, listOf(91, 35, 13, 8, 5, 1)),
        Puzzle(3465, listOf(99, 35, 33, 7, 5, 1)),
        Puzzle(3745, listOf(107, 35, 13, 8, 5, 1)),
        Puzzle(4025, listOf(115, 35, 23, 7, 5, 1))
    )

    private var currentPuzzleIndex = 0
    private lateinit var currentPuzzle: Puzzle

    private lateinit var mainLayout: View


    private lateinit var dbHandler: DBhandler
    private lateinit var auth: FirebaseAuth
    private var currentUserId: String = "local_user"


    private lateinit var timerText: TextView
    private lateinit var targetNumberText: TextView
    private lateinit var currentResultText: TextView
    private lateinit var expressionScrollView: HorizontalScrollView
    private lateinit var expressionText: TextView

    private lateinit var plusButton: Button
    private lateinit var minusButton: Button
    private lateinit var multiplyButton: Button
    private lateinit var divideButton: Button
    private lateinit var operatorButtons: List<Button>

    private lateinit var numberButtons: List<Button>

    private lateinit var loadAnotherNumberButton: Button

    private lateinit var winnerOverlay: View
    private lateinit var winnerTitleText: TextView
    private lateinit var winnerRankText: TextView
    private lateinit var winnerTimeText: TextView
    private lateinit var tierText: TextView
    private lateinit var continueButton: Button

    private val handler = Handler(Looper.getMainLooper())

    private var roundStartTime = 0L
    private var elapsedMillis = 0L

    private var currentResult: Double? = null
    private var selectedOperator: String? = null
    private var expression = ""

    private var roundWon = false
    private var autoContinueRunnable: Runnable? = null

    private val normalOperatorColor = Color.parseColor("#E63946")
    private val activeOperatorColor = Color.parseColor("#32E875")

    private val timerRunnable = object : Runnable {
        override fun run() {
            if (!roundWon) {
                elapsedMillis = System.currentTimeMillis() - roundStartTime
                timerText.text = "Time: ${formatTime(elapsedMillis)}"
                handler.postDelayed(this, 100)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_numbers_game)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + 20, systemBars.top + 20, systemBars.right + 20, systemBars.bottom + 20)
            insets
        }

        dbHandler = DBhandler(this)
        auth = FirebaseAuth.getInstance()
        currentUserId = auth.currentUser?.uid ?: "local_user"

        connectViews()
        setupOperatorButtons()
        setupNumberButtons()
        setupOtherButtons()

        loadPuzzle(0)
    }

    private fun connectViews() {
        mainLayout = findViewById(R.id.main)

        timerText = findViewById(R.id.timerText)
        targetNumberText = findViewById(R.id.targetNumberText)
        currentResultText = findViewById(R.id.currentResultText)
        expressionScrollView = findViewById(R.id.expressionScrollView)
        expressionText = findViewById(R.id.expressionText)

        plusButton = findViewById(R.id.plusButton)
        minusButton = findViewById(R.id.minusButton)
        multiplyButton = findViewById(R.id.multiplyButton)
        divideButton = findViewById(R.id.divideButton)

        tierText = findViewById(R.id.rankText)
        tierText.setText(dbHandler.getCurrentSeasonTier())

        operatorButtons = listOf(
            plusButton,
            minusButton,
            multiplyButton,
            divideButton
        )

        numberButtons = listOf(
            findViewById(R.id.numberButton1),
            findViewById(R.id.numberButton2),
            findViewById(R.id.numberButton3),
            findViewById(R.id.numberButton4),
            findViewById(R.id.numberButton5),
            findViewById(R.id.numberButton6)
        )

        loadAnotherNumberButton = findViewById(R.id.loadAnotherNumberButton)

        winnerOverlay = findViewById(R.id.winnerOverlay)
        winnerTitleText = findViewById(R.id.winnerTitleText)
        winnerRankText = findViewById(R.id.winnerRankText)
        winnerTimeText = findViewById(R.id.winnerTimeText)
        continueButton = findViewById(R.id.continueButton)
    }

    private fun setupOperatorButtons() {
        plusButton.setOnClickListener {
            selectOperator("+")
        }

        minusButton.setOnClickListener {
            selectOperator("-")
        }

        multiplyButton.setOnClickListener {
            selectOperator("×")
        }

        divideButton.setOnClickListener {
            selectOperator("÷")
        }
    }

    private fun setupNumberButtons() {
        numberButtons.forEach { button ->
            button.setOnClickListener {
                val number = button.text.toString().toInt()
                handleNumberClick(number)
            }
        }
    }

    private fun setupOtherButtons() {
        loadAnotherNumberButton.setOnClickListener {
            loadNextPuzzle()
        }

        continueButton.setOnClickListener {
            continueAfterWin()
        }
    }

    private fun loadPuzzle(index: Int) {
        currentPuzzleIndex = index
        currentPuzzle = puzzles[currentPuzzleIndex]

        targetNumberText.text = currentPuzzle.target.toString()

        for (i in numberButtons.indices) {
            numberButtons[i].text = currentPuzzle.numbers[i].toString()
        }

        resetRoundState()
        startTimer()
    }

    private fun loadNextPuzzle() {
        val nextIndex = (currentPuzzleIndex + 1) % puzzles.size
        loadPuzzle(nextIndex)
    }

    private fun resetRoundState() {
        handler.removeCallbacks(timerRunnable)

        autoContinueRunnable?.let {
            handler.removeCallbacks(it)
        }
        autoContinueRunnable = null

        currentResult = null
        selectedOperator = null
        expression = ""
        roundWon = false
        elapsedMillis = 0L

        currentResultText.text = "—"
        expressionText.text = "Start building..."
        timerText.text = "Time: 00:00"

        winnerOverlay.visibility = View.GONE
        clearBlur()
        clearActiveOperator()
    }

    private fun startTimer() {
        roundStartTime = System.currentTimeMillis()
        handler.post(timerRunnable)
    }

    private fun handleNumberClick(number: Int) {
        if (roundWon) return

        val oldResult = currentResult

        if (oldResult == null) {
            currentResult = number.toDouble()
            expression = number.toString()
            updateDisplay()
            checkForWin()
            return
        }

        val operator = selectedOperator

        if (operator == null) {
            // One-way calculator style:
            // after a number, the player must choose an operator before another number.
            return
        }

        val newResult = calculate(oldResult, operator, number.toDouble())

        if (newResult == null) {
            // Division by zero or invalid math.
            selectedOperator = null
            clearActiveOperator()
            return
        }

        currentResult = newResult
        expression += " $operator $number"

        selectedOperator = null
        clearActiveOperator()

        updateDisplay()
        checkForWin()
    }

    private fun selectOperator(operator: String) {
        if (roundWon) return

        // The player cannot start with an operator.
        if (currentResult == null) return

        selectedOperator = operator
        updateActiveOperator(operator)
    }

    private fun calculate(left: Double, operator: String, right: Double): Double? {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "×" -> left * right
            "÷" -> {
                if (right == 0.0) {
                    null
                } else {
                    left / right
                }
            }
            else -> null
        }
    }

    private fun updateDisplay() {
        val result = currentResult ?: return

        currentResultText.text = formatResult(result)
        expressionText.text = expression

        expressionScrollView.post {
            expressionScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }
    }

    private fun checkForWin() {
        val result = currentResult ?: return
        val target = currentPuzzle.target.toDouble()

        if (abs(result - target) < 0.000001) {
            showWinner()
        }
    }

    private fun showWinner() {
        if (roundWon) return

        roundWon = true
        handler.removeCallbacks(timerRunnable)

        elapsedMillis = System.currentTimeMillis() - roundStartTime

        val savedScore = dbHandler.saveSixNumbersGameResult(
            uid = currentUserId,
            target = currentPuzzle.target,
            numbers = currentPuzzle.numbers,
            expression = expression,
            finalResult = currentResult ?: 0.0,
            timeTakenMillis = elapsedMillis
        )

        dbHandler.syncUnsyncedGameResultsToFirestore(this, currentUserId) { success, count ->
            // Optional: no UI needed here.
            // Local save already happened, so failed sync is safe.
        }

        winnerTitleText.text = "MATCH!"

        val pointsText = if (savedScore.pointsEarned >= 0) {
            "+${savedScore.pointsEarned} Points"
        } else {
            "${savedScore.pointsEarned} Points"
        }

        winnerRankText.text = "$pointsText • ${savedScore.tierAfter}"
        winnerTimeText.text = "Time: ${formatTimeWithDecimal(elapsedMillis)} • Rank ${savedScore.rank}"

        winnerOverlay.visibility = View.VISIBLE
        applyBlur()

        autoContinueRunnable = Runnable {
            continueAfterWin()
        }

        handler.postDelayed(autoContinueRunnable!!, 2000)
    }

    private fun continueAfterWin() {
        autoContinueRunnable?.let {
            handler.removeCallbacks(it)
        }
        autoContinueRunnable = null

        winnerOverlay.visibility = View.GONE
        clearBlur()

        loadNextPuzzle()
    }

    private fun updateActiveOperator(operator: String) {
        clearActiveOperator()

        val activeButton = when (operator) {
            "+" -> plusButton
            "-" -> minusButton
            "×" -> multiplyButton
            "÷" -> divideButton
            else -> null
        }

        activeButton?.backgroundTintList = ColorStateList.valueOf(activeOperatorColor)
    }

    private fun clearActiveOperator() {
        operatorButtons.forEach { button ->
            button.backgroundTintList = ColorStateList.valueOf(normalOperatorColor)
        }
    }

    private fun calculateRank(timeMillis: Long): String {
        val seconds = timeMillis / 1000.0

        return when {
            seconds <= 10.0 -> "S"
            seconds <= 20.0 -> "A"
            seconds <= 35.0 -> "B"
            seconds <= 60.0 -> "C"
            else -> "D"
        }
    }

    private fun formatTime(timeMillis: Long): String {
        val totalSeconds = timeMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun formatTimeWithDecimal(timeMillis: Long): String {
        val seconds = timeMillis / 1000.0
        return String.format("%.1fs", seconds)
    }

    private fun formatResult(value: Double): String {
        return if (abs(value - value.toInt()) < 0.000001) {
            value.toInt().toString()
        } else {
            String.format("%.2f", value)
        }
    }

    private fun applyBlur() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mainLayout.setRenderEffect(
                RenderEffect.createBlurEffect(
                    18f,
                    18f,
                    Shader.TileMode.CLAMP
                )
            )
        }
    }

    private fun clearBlur() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mainLayout.setRenderEffect(null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable)

        autoContinueRunnable?.let {
            handler.removeCallbacks(it)
        }
    }
}