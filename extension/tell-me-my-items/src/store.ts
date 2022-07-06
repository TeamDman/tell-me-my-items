import * as vscode from 'vscode';

export class Store {
    private items = [];
    public getItems(): string[] {
        return this.items;
    }
    private getContent(path: string): Promise<string> {
        return new Promise((res, rej) => {
            vscode.workspace.openTextDocument(path).then(doc => res(doc.getText()));
        });
    }
    public async loadItemsFromDisk(path: string) {
        const content = await this.getContent(path);
        const converted = JSON.parse(content);
        this.items = converted.map((x:any) => {
            let rtn = x.id;
            if (x.data !== undefined) {
                rtn += " " + x.data;
            }
            return rtn;
        });
        // this.items = JSON.parse(FileSystemProv(path,{encoding:"utf8",flag:"r"})!);
    }
}