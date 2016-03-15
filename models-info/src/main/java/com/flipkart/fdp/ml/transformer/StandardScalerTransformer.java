package com.flipkart.fdp.ml.transformer;

import com.flipkart.fdp.ml.modelinfo.StandardScalerModelInfo;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Transforms input/ predicts for a Standard Scalar model representation
 * captured by  {@link com.flipkart.fdp.ml.modelinfo.StandardScalerModelInfo}.
 */
public class StandardScalerTransformer implements Transformer {
    private final StandardScalerModelInfo modelInfo;

    public StandardScalerTransformer(final StandardScalerModelInfo modelInfo) {
        this.modelInfo = modelInfo;
    }

    public double[] predict(final double[] input) {

        if(modelInfo.isWithMean()) {
            if(input.length != modelInfo.getMean().length) {
                throw new IllegalArgumentException("Size of input vector and mean are different : "
                        + input.length + " and " + modelInfo.getMean().length);
            }
            for( int i =0 ; i < input.length; i++) {
                input[i] -= modelInfo.getMean()[i];
            }
        }

        if(modelInfo.isWithStd()) {
            if(input.length != modelInfo.getStd().length) {
                throw new IllegalArgumentException("Size of std and input vector are different : "
                        + input.length + " and " + modelInfo.getStd().length);
            }
            for( int i=0 ; i < input.length; i++) {
                double stdi = modelInfo.getStd()[i];
                if(stdi != 0.0) {
                    input[i] /= stdi;
                }else{
                    input[i] = 0.0;
                }
            }
        }
        return input;
    }

    @Override
    public Object[] transform(Object[] input) {
        return ArrayUtils.toObject(predict(ArrayUtils.toPrimitive((Double [])input)));
    }
}