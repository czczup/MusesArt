// Copyright 2019 The MACE Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package czcz94cz.photovalley.styleTransfer;


import java.util.ArrayList;
import java.util.List;

import czcz94cz.photovalley.common.ModelConfig;
import czcz94cz.photovalley.common.ModelInfo;
import czcz94cz.photovalley.common.Shape;
import czcz94cz.photovalley.model.Filter;

public class ModelSelector {

    private static String CPU_DEVICE = "CPU";
    private static String GPU_DEVICE = "GPU";
    private static String DSP_DEVICE = "DSP";
    private static Shape[] shape = new Shape[]{new Shape(new int[]{1, 960, 960, 3})};

    private static ModelInfo[] modelInfos = new ModelInfo[] {
            new ModelInfo(0, "原图", null,
                    "", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(1, "缪斯", "https://s1.ax1x.com/2020/04/18/JntoLD.jpg",
                    "la_muse", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(2, "彩色方块", "https://s1.ax1x.com/2020/04/14/GxpePS.jpg",
                    "block", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(3, "奶黄", "https://s1.ax1x.com/2020/04/18/JntXWt.jpg",
                    "custard", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(4, "黑夜", "https://s1.ax1x.com/2020/04/18/JnNkYn.jpg",
                    "night", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(5, "冷色调", "https://s1.ax1x.com/2020/04/18/Jn32md.jpg",
                    "udnie", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(6, "水粉颜料", "https://s1.ax1x.com/2020/04/20/JMLrUP.jpg",
                    "gouache", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(7, "糖果", "https://s1.ax1x.com/2020/04/20/JML3Ax.jpg",
                    "candy", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(8, "赤色", "https://s1.ax1x.com/2020/04/20/JMLAA0.jpg",
                    "redness", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(9, "梦魇", "https://s1.ax1x.com/2020/04/24/Jr9HxA.jpg",
                    "rain", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(10, "浓墨重彩", "https://s1.ax1x.com/2020/04/24/Jr972d.jpg",
                    "face", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(11, "水彩", "https://s1.ax1x.com/2020/04/24/JrCFrq.jpg",
                    "watercolor", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(12, "毕加索", "https://s1.ax1x.com/2020/04/24/Jr9jVf.jpg",
                    "picasso", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(13, "抽象线条", "https://s1.ax1x.com/2020/04/26/Jgdtzt.jpg",
            "04line", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(14, "金秋", "https://s1.ax1x.com/2020/05/29/tnozp4.jpg",
                    "m09", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(15, "蓝海", "https://s1.ax1x.com/2020/05/29/tnojtU.jpg",
                    "m11", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(16, "沙丘", "https://s1.ax1x.com/2020/05/29/tnovhF.jpg",
                    "m12", new String[]{"input"}, shape, new String[]{"output"}, shape),
            new ModelInfo(17, "墨韵", "https://s1.ax1x.com/2020/05/29/tnTS1J.jpg",
                    "m13", new String[]{"input"}, shape, new String[]{"output"}, shape),

    };

    public static List<Filter> getModelInfo(String originalImageString) {
        List<Filter> filterList = new ArrayList<>();
        for (int i=0; i < modelInfos.length; i++) {
            ModelInfo modelInfo = modelInfos[i];
            Filter filter;
            if (i == 0) {
                filter = new Filter(modelInfo.getId(), modelInfo.getName(),
                        originalImageString, modelInfo.getFilename());
            } else {
                filter = new Filter(modelInfo.getId(), modelInfo.getName(),
                        modelInfo.getCoverImage(), modelInfo.getFilename());
            }
            filterList.add(filter);
        }
        return filterList;
    }

    public static ModelConfig select(int id) {
        return new ModelConfig(modelInfos[id], GPU_DEVICE);
    }
}
