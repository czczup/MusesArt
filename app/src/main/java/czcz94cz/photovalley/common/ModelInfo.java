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
package czcz94cz.photovalley.common;


public class ModelInfo {
    private String filename;
    private String[] inputNames;
    private Shape[] inputShapes;
    private String[] outputNames;
    private Shape[] outputShapes;
    private int id;
    private String name;
    private String coverImage;

    public ModelInfo(int id, String name, String coverImage, String filename, String[] inputNames, Shape[] inputShapes,
                     String[] outputNames, Shape[] outputShapes) {
        this.id = id;
        this.name = name;
        this.coverImage = coverImage;
        this.filename = filename;
        this.inputNames = inputNames;
        this.inputShapes = inputShapes;
        this.outputNames = outputNames;
        this.outputShapes = outputShapes;
    }

    public String getFilename() {
        return filename;
    }

    public String[] getInputNames() {
        return inputNames;
    }

    public Shape[] getInputShapes() {
        return inputShapes;
    }

    public String[] getOutputNames() {
        return outputNames;
    }

    public Shape[] getOutputShapes() {
        return outputShapes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoverImage() {
        return coverImage;
    }
}
