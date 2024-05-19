package exp

import chisel3._
import chisel3.util._
import chisel3.experimental._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import chisel3.simulator.EphemeralSimulator._
import chisel3.experimental.BundleLiterals._
import fixedpoint._
// import scala.language.postfixOps

class FixedPointExpTest extends AnyFreeSpec with Matchers {
    val wholeWidth: Int = 16
    val fractionalWidth: Int = 8
    val pow2 = scala.math.pow(2, fractionalWidth)
    
    "FixedExp Test" in {
        simulate(new FixedPointExp(wholeWidth, fractionalWidth)) { dut =>
            val start = -5
            val end = 0
            val step = 1

            for (value <- start until end by step) {
                val floatValue = value.toFloat / 10
                dut.io.x.poke(floatValue.F(fractionalWidth.BP).asSInt)
                dut.clock.step()
                val actualValue = scala.math.exp(floatValue)

                println(s"Test Case for input $floatValue: Exp = ${dut.io.exp_x.peek().litValue.toFloat / pow2}, Actual Value = $actualValue, Ref Err = ${((actualValue-dut.io.exp_x.peek().litValue.toFloat / pow2)/actualValue).abs * 100} %")

            }
        }
    }
}
