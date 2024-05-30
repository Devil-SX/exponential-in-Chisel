package exp

import chisel3._
import chisel3.util._
import chisel3.experimental._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import chisel3.simulator.EphemeralSimulator._
import chisel3.experimental.BundleLiterals._
import fixedpoint._
import java.io._

// import scala.language.postfixOps

class FixedPointExpTest extends AnyFreeSpec with Matchers {
    val wholeWidth: Int = 8
    val fractionalWidth: Int = 7
    val pow2 = scala.math.pow(2, fractionalWidth)
    
    "FixedExp Test" in {
        simulate(new FixedPointExp(wholeWidth, fractionalWidth)) { dut =>
            val start_val = -0.5
            val end_val = -0.002
            val num = 500
            val writer = new PrintWriter(new File("test_results.csv"))

            writer.write("Input Value,Computed Exp,Actual Exp,Relative Error (%)\n")

            val start_iter = (start_val * num).toInt
            val end_iter = (end_val * num).toInt

            for (value <- start_iter until end_iter) {
                val floatValue = value.toFloat / num
                dut.io.x.poke(floatValue.F(fractionalWidth.BP).asSInt)
                dut.clock.step()
                val actualValue = scala.math.exp(floatValue)
                val computedValue = dut.io.exp_x.peek().litValue.toFloat / pow2
                val relativeError = ((actualValue - computedValue) / actualValue).abs * 100

                writer.write(f"$floatValue%.2f,$computedValue%.5f,$actualValue%.5f,$relativeError%.2f\n")
            }

            writer.close()
        }
    }
}
