
�
input

Variable:0Conv2D:0Conv2D"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:
�� 
�
Conv2D:0moments/mean:0moments/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format: 
�
Conv2D:0
moments/mean:0sub:0sub"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
�� 
�
Conv2D:0
moments/mean:0moments/variance:0moments/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:
�� 
�
moments/variance:0add:0add"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format: 
�
add:0Rsqrt:0Rsqrt"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format: 
}
sub:0
Rsqrt:0mul:0mul"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
�� 
�
Variable_2:0
mul:0mul_1:0mul_1"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
�� 
�
mul_1:0
Variable_1:0add_1:0add_1"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:
�� 
�
add_1:0Relu:0Relu"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:
�� 
�
Relu:0
Variable_3:0
Conv2D_1:0Conv2D_1"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:
��@
�

Conv2D_1:0moments_1/mean:0moments_1/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:@
�

Conv2D_1:0
moments_1/mean:0sub_1:0sub_1"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
��@
�

Conv2D_1:0
moments_1/mean:0moments_1/variance:0moments_1/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:
��@
�
moments_1/variance:0add_2:0add_2"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:@
�
add_2:0	Rsqrt_1:0Rsqrt_1"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:@
�
sub_1:0
	Rsqrt_1:0mul_2:0mul_2"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
��@
�
Variable_5:0
mul_2:0mul_3:0mul_3"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
��@
�
mul_3:0
Variable_4:0add_3:0add_3"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:
��@
�
add_3:0Relu_1:0Relu_1"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:
��@
�
Relu_1:0
Variable_6:0
Conv2D_2:0Conv2D_2"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�

Conv2D_2:0moments_2/mean:0moments_2/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�

Conv2D_2:0
moments_2/mean:0sub_2:0sub_2"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�

Conv2D_2:0
moments_2/mean:0moments_2/variance:0moments_2/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_2/variance:0add_4:0add_4"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_4:0	Rsqrt_2:0Rsqrt_2"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_2:0
	Rsqrt_2:0mul_4:0mul_4"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_8:0
mul_4:0mul_5:0mul_5"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_5:0
Variable_7:0add_5:0add_5"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_5:0Relu_2:0Relu_2"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:���
�
Relu_2:0
Variable_9:0
Conv2D_3:0Conv2D_3"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�

Conv2D_3:0moments_3/mean:0moments_3/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�

Conv2D_3:0
moments_3/mean:0sub_3:0sub_3"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�

Conv2D_3:0
moments_3/mean:0moments_3/variance:0moments_3/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_3/variance:0add_6:0add_6"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_6:0	Rsqrt_3:0Rsqrt_3"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_3:0
	Rsqrt_3:0mul_6:0mul_6"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_11:0
mul_6:0mul_7:0mul_7"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_7:0
Variable_10:0add_7:0add_7"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_7:0Relu_3:0Relu_3"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:���
�
Relu_3:0
Variable_12:0
Conv2D_4:0Conv2D_4"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�

Conv2D_4:0moments_4/mean:0moments_4/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�

Conv2D_4:0
moments_4/mean:0sub_4:0sub_4"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�

Conv2D_4:0
moments_4/mean:0moments_4/variance:0moments_4/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_4/variance:0add_8:0add_8"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_8:0	Rsqrt_4:0Rsqrt_4"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_4:0
	Rsqrt_4:0mul_8:0mul_8"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_14:0
mul_8:0mul_9:0mul_9"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_9:0
Variable_13:0add_9:0add_9"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
Relu_2:0
add_9:0add_10:0add_10"Eltwise2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_10:0
Variable_15:0
Conv2D_5:0Conv2D_5"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�

Conv2D_5:0moments_5/mean:0moments_5/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�

Conv2D_5:0
moments_5/mean:0sub_5:0sub_5"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�

Conv2D_5:0
moments_5/mean:0moments_5/variance:0moments_5/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_5/variance:0add_11:0add_11"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_11:0	Rsqrt_5:0Rsqrt_5"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_5:0
	Rsqrt_5:0mul_10:0mul_10"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_17:0
mul_10:0mul_11:0mul_11"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_11:0
Variable_16:0add_12:0add_12"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_12:0Relu_4:0Relu_4"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:���
�
Relu_4:0
Variable_18:0
Conv2D_6:0Conv2D_6"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�

Conv2D_6:0moments_6/mean:0moments_6/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�

Conv2D_6:0
moments_6/mean:0sub_6:0sub_6"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�

Conv2D_6:0
moments_6/mean:0moments_6/variance:0moments_6/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_6/variance:0add_13:0add_13"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_13:0	Rsqrt_6:0Rsqrt_6"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_6:0
	Rsqrt_6:0mul_12:0mul_12"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_20:0
mul_12:0mul_13:0mul_13"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_13:0
Variable_19:0add_14:0add_14"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_10:0
add_14:0add_15:0add_15"Eltwise2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_15:0
Variable_21:0
Conv2D_7:0Conv2D_7"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�

Conv2D_7:0moments_7/mean:0moments_7/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�

Conv2D_7:0
moments_7/mean:0sub_7:0sub_7"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�

Conv2D_7:0
moments_7/mean:0moments_7/variance:0moments_7/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_7/variance:0add_16:0add_16"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_16:0	Rsqrt_7:0Rsqrt_7"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_7:0
	Rsqrt_7:0mul_14:0mul_14"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_23:0
mul_14:0mul_15:0mul_15"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_15:0
Variable_22:0add_17:0add_17"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_17:0Relu_5:0Relu_5"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:���
�
Relu_5:0
Variable_24:0
Conv2D_8:0Conv2D_8"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�

Conv2D_8:0moments_8/mean:0moments_8/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�

Conv2D_8:0
moments_8/mean:0sub_8:0sub_8"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�

Conv2D_8:0
moments_8/mean:0moments_8/variance:0moments_8/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_8/variance:0add_18:0add_18"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_18:0	Rsqrt_8:0Rsqrt_8"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_8:0
	Rsqrt_8:0mul_16:0mul_16"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_26:0
mul_16:0mul_17:0mul_17"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_17:0
Variable_25:0add_19:0add_19"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_15:0
add_19:0add_20:0add_20"Eltwise2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_20:0
Variable_27:0
Conv2D_9:0Conv2D_9"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�

Conv2D_9:0moments_9/mean:0moments_9/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�

Conv2D_9:0
moments_9/mean:0sub_9:0sub_9"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�

Conv2D_9:0
moments_9/mean:0moments_9/variance:0moments_9/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_9/variance:0add_21:0add_21"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_21:0	Rsqrt_9:0Rsqrt_9"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_9:0
	Rsqrt_9:0mul_18:0mul_18"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_29:0
mul_18:0mul_19:0mul_19"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_19:0
Variable_28:0add_22:0add_22"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_22:0Relu_6:0Relu_6"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:���
�
Relu_6:0
Variable_30:0Conv2D_10:0	Conv2D_10"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�
Conv2D_10:0moments_10/mean:0moments_10/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�
Conv2D_10:0
moments_10/mean:0sub_10:0sub_10"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Conv2D_10:0
moments_10/mean:0moments_10/variance:0moments_10/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_10/variance:0add_23:0add_23"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_23:0
Rsqrt_10:0Rsqrt_10"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_10:0

Rsqrt_10:0mul_20:0mul_20"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_32:0
mul_20:0mul_21:0mul_21"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_21:0
Variable_31:0add_24:0add_24"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_20:0
add_24:0add_25:0add_25"Eltwise2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_25:0
Variable_33:0Conv2D_11:0	Conv2D_11"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�
Conv2D_11:0moments_11/mean:0moments_11/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�
Conv2D_11:0
moments_11/mean:0sub_11:0sub_11"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Conv2D_11:0
moments_11/mean:0moments_11/variance:0moments_11/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_11/variance:0add_26:0add_26"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_26:0
Rsqrt_11:0Rsqrt_11"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_11:0

Rsqrt_11:0mul_22:0mul_22"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_35:0
mul_22:0mul_23:0mul_23"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_23:0
Variable_34:0add_27:0add_27"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_27:0Relu_7:0Relu_7"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:���
�
Relu_7:0
Variable_36:0Conv2D_12:0	Conv2D_12"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:���
�
Conv2D_12:0moments_12/mean:0moments_12/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:	�
�
Conv2D_12:0
moments_12/mean:0sub_12:0sub_12"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Conv2D_12:0
moments_12/mean:0moments_12/variance:0moments_12/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
moments_12/variance:0add_28:0add_28"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:	�
�
add_28:0
Rsqrt_12:0Rsqrt_12"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:	�
�
sub_12:0

Rsqrt_12:0mul_24:0mul_24"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
Variable_38:0
mul_24:0mul_25:0mul_25"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:���
�
mul_25:0
Variable_37:0add_29:0add_29"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_25:0
add_29:0add_30:0add_30"Eltwise2
T2
framework_type 2
data_format�2
type 2
has_data_format:���
�
add_30:0
Variable_39:0
stack:0conv2d_transpose:0conv2d_transpose"Deconv2D2
T2
framework_type 2
data_format�2
padding2
strides002
dim00�0�0@:
��@
�
conv2d_transpose:0moments_13/mean:0moments_13/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:@
�
conv2d_transpose:0
moments_13/mean:0sub_13:0sub_13"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
��@
�
conv2d_transpose:0
moments_13/mean:0moments_13/variance:0moments_13/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:
��@
�
moments_13/variance:0add_31:0add_31"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:@
�
add_31:0
Rsqrt_13:0Rsqrt_13"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:@
�
sub_13:0

Rsqrt_13:0mul_26:0mul_26"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
��@
�
Variable_41:0
mul_26:0mul_27:0mul_27"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
��@
�
mul_27:0
Variable_40:0add_32:0add_32"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:
��@
�
add_32:0Relu_8:0Relu_8"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:
��@
�
Relu_8:0
Variable_42:0
	stack_1:0conv2d_transpose_1:0conv2d_transpose_1"Deconv2D2
T2
framework_type 2
data_format�2
padding2
strides002
dim00�0�0 :
�� 
�
conv2d_transpose_1:0moments_14/mean:0moments_14/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format: 
�
conv2d_transpose_1:0
moments_14/mean:0sub_14:0sub_14"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
�� 
�
conv2d_transpose_1:0
moments_14/mean:0moments_14/variance:0moments_14/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:
�� 
�
moments_14/variance:0add_33:0add_33"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format: 
�
add_33:0
Rsqrt_14:0Rsqrt_14"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format: 
�
sub_14:0

Rsqrt_14:0mul_28:0mul_28"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
�� 
�
Variable_44:0
mul_28:0mul_29:0mul_29"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
�� 
�
mul_29:0
Variable_43:0add_34:0add_34"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:
�� 
�
add_34:0Relu_9:0Relu_9"
Activation2
T2
framework_type 2
data_format�2

activation"RELU2
has_data_format:
�� 
�
Relu_9:0
Variable_45:0Conv2D_13:0	Conv2D_13"Conv2D2
T2
framework_type 2
data_format�2
padding2
strides002
	dilations002
wino_block_size:
��
�
Conv2D_13:0moments_15/mean:0moments_15/mean"Reduce2
T2
framework_type 2
data_format�2
reduce_type 2

axis002
keepdims2
has_data_format:
�
Conv2D_13:0
moments_15/mean:0sub_15:0sub_15"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
��
�
Conv2D_13:0
moments_15/mean:0moments_15/variance:0moments_15/SquaredDifference"SqrDiffMean2
T2
framework_type 2
data_format�2
type2
has_data_format:
��
�
moments_15/variance:0add_35:0add_35"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_inputo�:2
scalar_input_index2
has_data_format:
�
add_35:0
Rsqrt_15:0Rsqrt_15"Eltwise2
T2
framework_type 2
data_format�2
type	2
scalar_input   �2
has_data_format:
�
sub_15:0

Rsqrt_15:0mul_30:0mul_30"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
��
�
Variable_47:0
mul_30:0mul_31:0mul_31"Eltwise2
T2
framework_type 2
data_format�2
type2
has_data_format:
��
�
mul_31:0
Variable_46:0add_36:0add_36"BiasAdd2
T2
framework_type 2
data_format�2
type 2
has_data_format:
��
�
add_36:0Tanh:0Tanh"
Activation2
T2
framework_type 2
data_format�2

activation"TANH2
has_data_format:
��
�
Tanh:0mul_32:0mul_32"Eltwise2
T2
framework_type 2
data_format�2
type2
scalar_input  C2
scalar_input_index2
has_data_format:
��
�
mul_32:0Add_37:0Add_37"Eltwise2
T2
framework_type 2
data_format�2
type 2
scalar_input  �B2
scalar_input_index2
has_data_format:
��
�
Add_37:0output/Minimum:0output/Minimum"Eltwise2
T2
framework_type 2
data_format�2
type2
scalar_input  C2
scalar_input_index2
has_data_format:
��
�
output/Minimum:0outputoutput"Eltwise2
T2
framework_type 2
data_format�2
type2
scalar_input    2
scalar_input_index2
has_data_format:
��
filter_formate
data_format
opencl_mem_type*Variable_47:00 8*Variable_46:008 		*Variable_45:008�< *Variable_44:00�y8  *Variable_43:00�z8 *	stack_1:00�z8  @*Variable_42:00�z8��@*Variable_41:00��8@@*Variable_40:00��8@*stack:00��8"@�*Variable_39:00�8���*Variable_38:00�8��*Variable_37:00�8�#��*Variable_36:00�8��	�*Variable_35:00�8��*Variable_34:00�8�#��*Variable_33:00�8��	�*Variable_32:00�08��*Variable_31:00�08�#��*Variable_30:00�08��	�*Variable_29:00�B8��*Variable_28:00�B8�#��*Variable_27:00�B8��	�*Variable_26:00�T8��*Variable_25:00�T8�#��*Variable_24:00�T8��	�*Variable_23:00�f8��*Variable_22:00�f8�#��*Variable_21:00�f8��	�*Variable_20:00�x8��*Variable_19:00�x8�#��*Variable_18:00�x8��	�*Variable_17:00�8��*Variable_16:00�8�$��*Variable_15:00�8��	�*Variable_14:00�8��*Variable_13:00�8�$��*Variable_12:00���8��	�*Variable_11:00���8��*Variable_10:00�®8�#��*Variable_9:00�Į8��	�*Variable_8:00���8��*Variable_7:00���8�"�@*Variable_6:00���8��@*Variable_5:00���8@@*Variable_4:00���8@!@ *Variable_3:00���8�� *Variable_2:00���8  *Variable_1:00���8  		*
Variable:00���8�< �
input��(0�
output��(0