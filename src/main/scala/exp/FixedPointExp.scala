package exp

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage
import fixedpoint._


class FixedPointExp(val wholeWidth: Int, val fractionalWidth: Int) extends Module {
  val io = IO(new Bundle{
    val x = Input(SInt((wholeWidth).W))
    val exp_x = Output(SInt((wholeWidth).W))
  })

  // z = floor(-x/log2)
  val z = Wire(SInt(((wholeWidth).W)))
  val p = Wire(FixedPoint((wholeWidth).W, fractionalWidth.BP))
  val lp = Wire(FixedPoint((wholeWidth).W, fractionalWidth.BP))
  val log2 = WireDefault(0.6931471805599453.F(fractionalWidth.BP))
  val bias1 = WireDefault(1.353.F(fractionalWidth.BP))
  val k1  = WireDefault(0.3585.F(fractionalWidth.BP))
  val bias2 = WireDefault(0.344.F(fractionalWidth.BP))

  z := io.x / log2.asSInt
  p := io.x.asFixedPoint(fractionalWidth.BP) + z.asFixedPoint(fractionalWidth.BP) * log2
  lp := k1 * (p + bias1) * (p + bias1) + bias2
  io.exp_x := (lp >> z.asUInt).asSInt
}

object FixedPointExp extends App {
  ChiselStage.emitSystemVerilogFile(
    new FixedPointExp(16, 8),
    Array("--target-dir", "verilog/"),
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info"),
  )
}