Fixed Point EXP in Chisel 6.2

Implementation of exp(x) in [I-BERT: Integer-only BERT Quantization](https://arxiv.org/abs/2101.01321)

```shell
mill Exp.test.testOnly  exp.FixedPointExpTest
```

```shell
Test Case for input -0.5: Exp = 0.6015625, Actual Value = 0.6065306597126334, Ref Err = 0.8191110594454163 %
Test Case for input -0.4: Exp = 0.66796875, Actual Value = 0.6703200420402204, Ref Err = 0.35077155578758024 %
Test Case for input -0.3: Exp = 0.73828125, Actual Value = 0.7408182118504766, Ref Err = 0.3424540339173831 %
Test Case for input -0.2: Exp = 0.8203125, Actual Value = 0.8187307506379741, Ref Err = 0.19319530392542292 %
Test Case for input -0.1: Exp = 0.90234375, Actual Value = 0.9048374166876467, Ref Err = 0.27559279066678405 %
```

# Oops

[Fixed Point Arithmetic](https://github.com/ucb-bar/fixedpoint) is implemented in Chisel 5.1, migrate to Chisel 6.2 by modifying the following code in `FixedPoint.scala`

```
// import chisel3.internal.firrtl.{KnownWidth, UnknownWidth, Width}
import chisel3.{KnownWidth, UnknownWidth, Width}
```