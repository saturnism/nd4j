/*-
 *
 *  * Copyright 2015 Skymind,Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package org.nd4j.linalg.api.ops.impl.transforms.arithmetic;

import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.imports.NoOpNameFoundException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.BaseTransformOp;

import java.util.ArrayList;
import java.util.List;

/**
 * Multiplication operation
 *
 * @author Adam Gibson
 */
public class OldMulOp extends BaseTransformOp {
    public OldMulOp(SameDiff sameDiff, SDVariable i_v1, SDVariable i_v2) {
        super(sameDiff, i_v1, i_v2);
    }

    public OldMulOp(SameDiff sameDiff, SDVariable i_v1, SDVariable i_v2, boolean inPlace) {
        super(sameDiff, i_v1, i_v2, inPlace);
    }

    public OldMulOp() {}

    public OldMulOp(INDArray x, INDArray y, INDArray z, long n) {
        super(x, y, z, n);
    }

    public OldMulOp(INDArray x) {
        super(x);
    }

    public OldMulOp(INDArray x, INDArray z) {
        super(x, z);
    }

    public OldMulOp(INDArray x, INDArray z, long n) {
        super(x, z, n);
    }

    public OldMulOp(INDArray x, INDArray y, INDArray z) {
        super(x, y, z, x.lengthLong());
    }

    @Override
    public int opNum() {
        return 6;
    }

    @Override
    public String opName() {
        return "oldmul";
    }


    @Override
    public String onnxName() {
        throw new NoOpNameFoundException("No onnx op opName found for " +  opName());
    }

    @Override
    public String tensorflowName() {
        throw new NoOpNameFoundException("No tensorflow op opName found for " +  opName());
    }

    @Override
    public void init(INDArray x, INDArray y, INDArray z, long n) {
        super.init(x, y, z, n);
        if (y == null)
            throw new IllegalArgumentException("No components to multiply");
    }


    @Override
    public List<SDVariable> doDiff(List<SDVariable> i_v) {
        SDVariable g = sameDiff.setupFunction(i_v.get(0));
        SDVariable gradWrtX = f().mul(g,rarg());
        SDVariable gradWrtY = f().mul(g,larg());
        List<SDVariable> ret = new ArrayList<>(2);
        ret.add(gradWrtX);
        ret.add(gradWrtY);
        return ret;
    }


}
